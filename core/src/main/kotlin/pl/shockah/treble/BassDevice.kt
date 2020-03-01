package pl.shockah.treble

import com.sun.jna.Platform
import com.sun.jna.Pointer
import com.sun.jna.WString
import java.io.File
import java.lang.ref.WeakReference
import java.net.URL
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicReference
import pl.shockah.treble.BassLibrary as L
import pl.shockah.treble.BassNativeConstants as C

class BassDevice(
		val id: Int,
		val description: String,
		val driver: String?
) {
	companion object {
		val NONE = BassDevice(0, "<no device>", "")
	}

	private var init = false

	var frequency: Int = 0
		private set

	val info: Info
		get() = Info(this)

	var volume: Float
		get() {
			setAsCurrent()
			val raw = L.lib.BASS_GetVolume()
			if (raw < 0f)
				L.throwErrorException()
			return raw
		}
		set(value) {
			setAsCurrent()
			val actualValue = value.coerceIn(0f, 1f)
			if (!L.lib.BASS_SetVolume(actualValue))
				L.throwErrorException()
		}

	override fun toString(): String {
		val builder = StringBuilder(description)
		driver?.takeIf { !it.isEmpty() }?.let { builder.append(" ($it)") }
		return builder.toString()
	}

	fun setAsCurrent() {
		if (!L.lib.BASS_SetDevice(id))
			L.throwErrorException()
	}

	private fun internalInit(frequency: Int?, flags: BassInitFlag = BassInitFlag.None) {
		if (init)
			return

		init = true
		this.frequency = frequency ?: 48000
		val actualFlags = flags.nativeValue or (frequency?.let { C.BASS_DEVICE_FREQ } ?: 0)
		if (!L.lib.BASS_Init(id, this.frequency, actualFlags, 0, null))
			L.throwErrorException()
	}

	fun init(frequency: Int, flags: BassInitFlag = BassInitFlag.None) {
		internalInit(frequency, flags)
	}

	fun init(flags: BassInitFlag = BassInitFlag.None) {
		internalInit(null, flags)
	}

	protected fun finalize() {
		free()
	}

	fun free() {
		if (!init)
			return

		init = false
		setAsCurrent()
		if (!L.lib.BASS_Free())
			L.throwErrorException()
	}

	fun <T: BassChannel> newChannel(closure: () -> T): T {
		init()
		return closure().also { L.channels += it }
	}

	fun createStream(frequency: Int? = null, channels: Int? = null, processor: BassStreamProcessor, flags: BassStreamFlag = BassStreamFlag.None): BassStream {
		return newChannel {
			BassStream(createPredefinedStream(frequency, channels, Pointer(processor.nativeValue.toLong()), flags), this, BassStreamFlag.FreeAutomatically in flags)
		}
	}

	fun createPushStream(frequency: Int? = null, channels: Int? = null, flags: BassStreamFlag = BassStreamFlag.None): BassPushStream {
		return newChannel {
			BassPushStream(createPredefinedStream(frequency, channels, Pointer(C.STREAMPROC_PUSH.toLong()), flags), this, BassStreamFlag.FreeAutomatically in flags)
		}
	}

	private fun createPredefinedStream(frequency: Int? = null, channels: Int? = null, processor: Pointer, flags: BassStreamFlag = BassStreamFlag.None): Int {
		setAsCurrent()
		val result = L.lib.BASS_StreamCreate(frequency ?: this.frequency, channels ?: info.availableSpeakers.takeIf { it > 0 } ?: 2, flags.nativeValue, processor, null)
		if (result == 0)
			L.throwErrorException()
		return result
	}

	fun createStream(frequency: Int? = null, channels: Int? = null, processor: (stream: BassStream, buffer: ByteBuffer) -> Int, flags: BassStreamFlag = BassStreamFlag.None): BassStream {
		val reference = AtomicReference<WeakReference<BassStream>>()
		val actualProcessor = object : BassNativeLibrary.STREAMPROC {
			override fun invoke(handle: Int, buffer: Pointer, length: Int, userData: Pointer?): Int {
				reference.get().get()?.let { stream ->
					val actualBuffer = buffer.getByteBuffer(0, length.toLong())
					return processor(stream, actualBuffer)
				}
				return 0
			}
		}

		return newChannel {
			setAsCurrent()
			val result = L.lib.BASS_StreamCreate(frequency ?: this.frequency, channels ?: info.availableSpeakers.takeIf { it > 0 } ?: 2, flags.nativeValue, actualProcessor, null)
			if (result == 0)
				L.throwErrorException()
			val stream = BassStream(result, this, BassStreamFlag.FreeAutomatically in flags)
			reference.set(WeakReference(stream))
			return@newChannel stream
		}
	}

	fun createFileStream(file: File, offset: Int = 0, length: Int? = null, flags: BassFileStreamFlag = BassFileStreamFlag.None): BassBufferStream {
		return newChannel {
			setAsCurrent()
			val result = unicodeStreamCreateFile(false, file.absoluteFile.normalize().absolutePath, offset.toLong(), (length ?: 0).toLong(), flags.nativeValue)
			if (result == 0)
				L.throwErrorException()
			return@newChannel BassBufferStream(result, this, BassFileStreamFlag.FreeAutomatically in flags)
		}
	}

	private fun unicodeStreamCreateFile(memory: Boolean, file: String, offset: Long, length: Long, flags: Int): Int {
		return when {
			Platform.isWindows() -> L.lib.BASS_StreamCreateFile(memory, WString(file), offset, length, flags or C.BASS_UNICODE_PLATFORM.toInt())
			else -> L.lib.BASS_StreamCreateFile(memory, file, offset, length, flags)
		}
	}

	fun createMemoryStream(buffer: ByteBuffer, flags: BassFileStreamFlag = BassFileStreamFlag.None): BassBufferStream {
		return newChannel {
			setAsCurrent()
			val result = L.lib.BASS_StreamCreateFile(true, buffer, 0L, 0L, flags.nativeValue)
			if (result == 0)
				L.throwErrorException()
			return@newChannel BassBufferStream(result, this, BassFileStreamFlag.FreeAutomatically in flags)
		}
	}

	fun createUrlStream(url: URL, offset: Int = 0, processor: ((stream: BassStream, buffer: ByteBuffer) -> Int)? = null, flags: BassUrlStreamFlag = BassUrlStreamFlag.None): BassStream {
		val reference = AtomicReference<WeakReference<BassStream>>()
		val actualProcessor = processor?.let { object : BassNativeLibrary.DOWNLOADPROC {
			override fun invoke(buffer: Pointer?, length: Int, userData: Pointer?) {
				reference.get().get()?.let { stream ->
					buffer?.let { buffer ->
						val actualBuffer = buffer.getByteBuffer(0, length.toLong())
						it(stream, actualBuffer)
					}
				}
			}
		} }

		return newChannel {
			setAsCurrent()
			val result = unicodeStreamCreateUrl(url.toExternalForm(), offset, flags.nativeValue, actualProcessor, null)
			if (result == 0)
				L.throwErrorException()
			val stream = BassStream(result, this, BassUrlStreamFlag.FreeAutomatically in flags)
			reference.set(WeakReference(stream))
			return@newChannel stream
		}
	}

	private fun unicodeStreamCreateUrl(url: String, offset: Int, flags: Int, processor: BassNativeLibrary.DOWNLOADPROC?, userData: Pointer?): Int {
		return when {
			Platform.isWindows() -> L.lib.BASS_StreamCreateURL(WString(url), offset, flags or C.BASS_UNICODE_PLATFORM.toInt(), processor, userData)
			else -> L.lib.BASS_StreamCreateURL(url, offset, flags, processor, userData)
		}
	}

	fun loadMusic(file: File, offset: Int = 0, length: Int? = null, flags: BassMusicFlag = BassMusicFlag.None, frequency: Int? = null): BassMusic {
		return newChannel {
			setAsCurrent()
			val result = unicodeLoadMusic(false, file.absoluteFile.normalize().absolutePath, offset.toLong(), length ?: 0, flags.nativeValue, frequency ?: this.frequency)
			if (result == 0)
				L.throwErrorException()
			return@newChannel BassMusic(result, this, BassMusicFlag.FreeAutomatically in flags)
		}
	}

	private fun unicodeLoadMusic(memory: Boolean, file: String, offset: Long, length: Int, flags: Int, frequency: Int): Int {
		return when {
			Platform.isWindows() -> L.lib.BASS_MusicLoad(memory, WString(file), offset, length, flags or C.BASS_UNICODE_PLATFORM.toInt(), frequency)
			else -> L.lib.BASS_MusicLoad(memory, file, offset, length, flags, frequency)
		}
	}

	data class Info(
			private val capabilities: Int,
			private val flags: Int,
			val totalHardwareMemory: Int,
			val freeHardwareMemory: Int,
			val freeSampleSlots: Int,
			val free3DSlots: Int,
			val allowedFrequency: ClosedRange<Int>,
			val isEaxEnabled: Boolean,
			val minimumBufferLength: Int,
			val directSoundVersion: Int,
			val millisecondLatency: Int,
			val availableSpeakers: Int,
			val currentFrequency: Int
	) {
		companion object {
			internal operator fun invoke(device: BassDevice): Info {
				val info = BassNativeLibrary.BASS_INFO.ByReference()
				device.setAsCurrent()
				if (!L.lib.BASS_GetInfo(info))
					L.throwErrorException()

				return Info(
						info.flags,
						info.initflags,
						info.hwsize,
						info.hwfree,
						info.freesam,
						info.free3d,
						info.minrate..info.maxrate,
						info.eax,
						info.minbuf,
						info.dsver,
						info.latency,
						info.speakers,
						info.freq
				)
			}
		}

		operator fun get(flag: Int): Boolean {
			return flags and flag != 0
		}
	}
}