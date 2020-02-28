package pl.shockah.treble

import com.sun.jna.Pointer
import com.sun.jna.ptr.FloatByReference
import java.io.File
import java.lang.ref.WeakReference
import java.nio.Buffer
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicReference
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import pl.shockah.treble.BassLibrary as L
import pl.shockah.treble.BassNativeConstants as C

abstract class BassChannel(
		val handle: Int,
		initialDevice: BassDevice,
		protected val autoFree: Boolean
) {
	var freed = false
		protected set

	val info: Info
		get() = Info(this)

	var device: BassDevice by Delegates.vetoable(initialDevice) { _, old, new ->
		if (freed)
			throw IllegalStateException()

		if (old == new)
			return@vetoable true

		new.newChannel {
			if (!L.lib.BASS_ChannelSetDevice(handle, new.id))
				L.throwErrorException()
			return@newChannel this
		}

		return@vetoable true
	}

	var volume: Float by channelAttribute(C.BASS_ATTRIB_VOL, { it }, { it.coerceAtLeast(0f) })

	var frequency: Int by channelAttribute(C.BASS_ATTRIB_FREQ, { it.toInt() }, { it.toFloat() })

	var pan: Float by channelAttribute(C.BASS_ATTRIB_VOL, { it }, { it.coerceIn(-1f, 1f) })

	val state: BassChannelState
		get() {
			if (freed)
				throw IllegalStateException()

			val result = L.lib.BASS_ChannelIsActive(handle)
			if (result == BassChannelState.Stopped.nativeValue) {
				L.throwErrorExceptionIfAny()
				return BassChannelState.Stopped
			} else {
				return BassChannelState.values().first { it.nativeValue == result }
			}
		}

	val playbackBufferCurrentLength: Int
		get() {
			if (freed)
				throw IllegalStateException()

			val result = L.lib.BASS_ChannelGetData(handle, null, C.BASS_DATA_AVAILABLE)
			if (result == -1)
				L.throwErrorException()
			return result
		}

	protected fun finalize() {
		free()
	}

	protected abstract fun free(autoFree: Boolean)

	fun free() {
		free(false)
	}

	fun play(restart: Boolean = false) {
		if (freed)
			throw IllegalStateException()

		if (state == BassChannelState.Playing || state == BassChannelState.Stalled)
			return

		if (!L.lib.BASS_ChannelPlay(handle, restart))
			L.throwErrorException()
	}

	fun pause() {
		if (freed)
			throw IllegalStateException()

		if (state == BassChannelState.Paused || state == BassChannelState.Stopped)
			return

		if (!L.lib.BASS_ChannelPause(handle))
			L.throwErrorException()
	}

	fun stop() {
		if (freed)
			throw IllegalStateException()

		if (state == BassChannelState.Stopped)
			return

		if (!L.lib.BASS_ChannelStop(handle))
			L.throwErrorException()
		if (autoFree)
			free(true)
	}

	fun update(milliseconds: Int? = null) {
		if (freed)
			throw IllegalStateException()

		if (!L.lib.BASS_ChannelUpdate(handle, milliseconds ?: 0))
			L.throwErrorException()
	}

	fun getData(length: Int, flags: BassStreamDataFlag = BassStreamDataFlag.None): ByteBuffer {
		val buffer = ByteBuffer.allocateDirect(length)
		val byteCount = getData(buffer, length, flags)
		(buffer as Buffer).position(byteCount)
		(buffer as Buffer).flip()
		return buffer
	}

	fun getData(buffer: ByteBuffer, length: Int = buffer.capacity(), flags: BassStreamDataFlag = BassStreamDataFlag.None): Int {
		if (freed)
			throw IllegalStateException()
		if (!buffer.isDirect || length < 0 || length > buffer.capacity())
			throw IllegalArgumentException()

		val result = L.lib.BASS_ChannelGetData(handle, buffer, length or flags.nativeValue)
		if (result == -1)
			L.throwErrorException()
		return result
	}

	fun <T: BassSyncEvent> createSynchronizer(
			type: BassSyncEvent.Type<T>,
			mixTime: Boolean = false,
			onlyOnce: Boolean = false,
			callback: (channel: BassChannel, synchronizer: BassSynchronizer<T>, event: T) -> Unit
	): BassSynchronizer<T> {
		val reference = AtomicReference<WeakReference<BassSynchronizer<T>>>()
		val actualProcessor = object : BassNativeLibrary.SYNCPROC {
			override fun invoke(handle: Int, channel: Int, data: Int, userData: Pointer?) {
				reference.get().get()?.let { synchronizer ->
					callback(this@BassChannel, synchronizer, type.factory(data))
					if (onlyOnce)
						synchronizer.free(true)
				}
			}
		}

		var actualType = type.nativeType
		if (mixTime)
			actualType = actualType or C.BASS_SYNC_MIXTIME
		if (onlyOnce)
			actualType = actualType or C.BASS_SYNC_ONETIME.toInt()

		val result = L.lib.BASS_ChannelSetSync(handle, actualType, type.param, actualProcessor, null)
		if (result == 0)
			L.throwErrorException()
		val synchronizer = BassSynchronizer(result, this, type)
		reference.set(WeakReference(synchronizer))
		L.synchronizers += synchronizer
		return synchronizer
	}

	protected fun channelAttribute(attribute: Int): ReadWriteProperty<Any?, Float> {
		return channelAttribute(attribute, { it }, { it })
	}

	protected fun <T> channelAttribute(attribute: Int, getter: (raw: Float) -> T, setter: (value: T) -> Float): ReadWriteProperty<Any?, T> {
		return object : ReadWriteProperty<Any?, T> {
			override fun getValue(thisRef: Any?, property: KProperty<*>): T {
				if (freed)
					throw IllegalStateException()

				val value = FloatByReference()
				if (!L.lib.BASS_ChannelGetAttribute(handle, attribute, value))
					L.throwErrorException()
				return getter(value.value)
			}

			override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
				if (freed)
					throw IllegalStateException()

				if (!L.lib.BASS_ChannelSetAttribute(handle, C.BASS_ATTRIB_VOL, setter(value)))
					L.throwErrorException()
			}
		}
	}

	data class Info(
			val frequency: Int,
			val channels: Int,
			private val flags: Int,
			private val type: Int,
			val bitsPerSample: Int?,
			val plugin: Int?,
			val sample: Int?,
			val file: File?
	) {
		companion object {
			internal operator fun invoke(channel: BassChannel): Info {
				val info = BassNativeLibrary.BASS_CHANNELINFO.ByReference()
				if (!L.lib.BASS_ChannelGetInfo(channel.handle, info))
					L.throwErrorException()

				return Info(
						info.freq,
						info.chans,
						info.flags,
						info.ctype,
						info.origres.takeIf { it != 0 },
						info.plugin.takeIf { it != 0 },
						info.sample.takeIf { it != 0 },
						info.filename?.let { File(it) }
				)
			}
		}

		fun isFlagSet(flag: Int): Boolean {
			return flags and flag != 0
		}

		fun isType(type: Int): Boolean {
			return this.type and type != 0
		}
	}
}