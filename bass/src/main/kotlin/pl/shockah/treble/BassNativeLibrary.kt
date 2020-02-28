@file:Suppress("FunctionName", "ClassName")

package pl.shockah.treble

import com.sun.jna.*
import com.sun.jna.ptr.FloatByReference
import pl.shockah.treble.BassException.Handle
import pl.shockah.treble.BassException.NotAvailable
import java.nio.ByteBuffer
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import pl.shockah.treble.BassNativeConstants as C

interface BassNativeLibrary: Library {
	companion object {
		internal const val libName = "bass"

		private var internalInstance: BassNativeLibrary? = null

		private val lock = ReentrantLock()

		val instance: BassNativeLibrary
			get() = lock.withLock {
				if (internalInstance == null)
					loadLibrary()
				return internalInstance!!
			}

		fun loadLibrary(reload: Boolean = false) {
			lock.withLock {
				internalInstance?.let {
					if (reload)
						unloadLibrary()
					else
						return
				}
				val lib = JnaHelper.extractLibrary(libName)
				System.load(lib.absoluteFile.normalize().absolutePath)
				internalInstance = Native.synchronizedLibrary(Native.load(libName, BassNativeLibrary::class.java)) as BassNativeLibrary
			}
		}

		fun unloadLibrary() {
			lock.withLock {
				internalInstance?.let {
					NativeLibrary.getInstance(libName).dispose()
					internalInstance = null
				}
			}
		}
	}

	fun BASS_GetVersion(): Int

	fun BASS_ErrorGetCode(): Int

	// errors: IllegalParameter
	fun BASS_GetConfig(option: Int): Int

	// errors: IllegalParameter
	fun BASS_SetConfig(option: Int, value: Int): Boolean

	// errors: AudioSystemNotInstalled, Device, Already, Driver, Busy, Format, Memory, No3D, Unknown
	fun BASS_Init(device: Int, frequency: Int, flags: Int, window: Int /*HWND*/, clsid: Pointer? /*GUID **/): Boolean

	// errors: Init
	fun BASS_Free(): Boolean

	// errors: FileOpen, FileFormat, Version, Already
	fun BASS_PluginLoad(file: String, flags: Int): Int
	fun BASS_PluginLoad(file: WString, flags: Int): Int

	// errors: Handle
	fun BASS_PluginFree(handle: Int): Boolean

	// errors: Handle
	fun BASS_PluginGetInfo(handle: Int): BASS_PLUGININFO.ByReference

	// errors: Init
	fun BASS_GetDevice(): Int

	// errors: Device, Init
	fun BASS_SetDevice(device: Int): Boolean

	// errors: Device
	fun BASS_GetDeviceInfo(device: Int, info: BASS_DEVICEINFO): Boolean

	// errors: Init
	fun BASS_GetInfo(info: BASS_INFO.ByReference): Boolean

	// errors: Init, NotAvailable, Unknown
	fun BASS_GetVolume(): Float

	// errors: Init, NotAvailable, IllegalParameter, Unknown
	fun BASS_SetVolume(volume: Float): Boolean

	// errors: NotAvailable
	fun BASS_Update(length: Int): Boolean

	// errors: Handle
	fun BASS_ChannelGetDevice(handle: Int): Int

	// errors: Handle, Device, Init, Already, NotAvailable, Format, Memory, Unknown
	fun BASS_ChannelSetDevice(handle: Int, device: Int): Boolean

	// errors: Handle, Start, Decode, BufferLost, NoHardware
	fun BASS_ChannelPlay(handle: Int, restart: Boolean): Boolean

	// errors: NotPlaying, Decode, Already
	fun BASS_ChannelPause(handle: Int): Boolean

	// errors: Handle
	fun BASS_ChannelStop(handle: Int): Boolean

	// errors: Handle, NotAvailable, Ended, Unknown
	fun BASS_ChannelUpdate(handle: Int, length: Int): Boolean

	// errors: Handle, NotAvailable, IllegalType, other attribute-dependent
	fun BASS_ChannelGetAttribute(handle: Int, attribute: Int, value: FloatByReference): Boolean

	// errors: Handle, IllegalType, IllegalParameter, other attribute-dependent
	fun BASS_ChannelSetAttribute(handle: Int, attribute: Int, value: Float): Boolean

	// errors: Handle, Ended, NotAvailable, BufferLost
	fun BASS_ChannelGetData(handle: Int, buffer: ByteBuffer, length: Int): Int
	fun BASS_ChannelGetData(handle: Int, buffer: Pointer?, length: Int): Int

	// errors: Handle
	fun BASS_ChannelGetInfo(handle: Int, info: BASS_CHANNELINFO.ByReference): Boolean

	// errors: Handle
	fun BASS_ChannelIsActive(handle: Int): Int

	// errors: Handle, IllegalType, IllegalParameter
	fun BASS_ChannelSetSync(handle: Int, type: Int, param: Long, processor: SYNCPROC, userData: Pointer?): Int

	// errors: Handle
	fun BASS_ChannelRemoveSync(handle: Int, sync: Int): Boolean

	// errors: Init, NotAvailable, Format, Speaker, Memory, No3D, Unknown
	fun BASS_StreamCreate(frequency: Int, channels: Int, flags: Int, processor: Pointer, userData: Pointer?): Int
	fun BASS_StreamCreate(frequency: Int, channels: Int, flags: Int, processor: STREAMPROC, userData: Pointer?): Int

	// errors: Init, NotAvailable, IllegalParameter, FileOpen, FileFormat, Speaker, Memory, No3D, Unknown
	fun BASS_StreamCreateFile(memory: Boolean, file: ByteBuffer, offset: Long, length: Long, flags: Int): Int
	fun BASS_StreamCreateFile(memory: Boolean, file: String, offset: Long, length: Long, flags: Int): Int
	fun BASS_StreamCreateFile(memory: Boolean, file: WString, offset: Long, length: Long, flags: Int): Int

	// errors: Init, NotAvailable, NoNetwork, IllegalParameter, Ssl, Timeout, FileOpen, FileFormat, Codec, Format, Speaker, Memory, No3D, Unknown
	fun BASS_StreamCreateURL(url: String, offset: Int, flags: Int, processor: DOWNLOADPROC?, userData: Pointer?): Int
	fun BASS_StreamCreateURL(url: WString, offset: Int, flags: Int, processor: DOWNLOADPROC?, userData: Pointer?): Int

	// errors: Handle, NotAFile, NotAvailable
	fun BASS_StreamGetFilePosition(handle: Int, mode: Int): Long

	// errors: Handle
	fun BASS_StreamFree(handle: Int): Boolean

	// errors: Handle, NotAvailable, IllegalParameter, Ended, Memory
	fun BASS_StreamPutData(handle: Int, buffer: ByteBuffer?, length: Int): Int

	/** errors: [Handle] */
	fun BASS_ChannelBytes2Seconds(handle: Int, pos: Long): Double

	/** errors: [Handle], [NotAvailable] */
	fun BASS_ChannelGetLength(handle: Int, mode: Int): Long

	// errors: Init, NotAvailable, FileOpen, FileFormat, Speaker, Memory, No3D, Unknown
	fun BASS_MusicLoad(memory: Boolean, file: ByteBuffer, offset: Long, length: Int, flags: Int, frequency: Int): Int
	fun BASS_MusicLoad(memory: Boolean, file: String, offset: Long, length: Int, flags: Int, frequency: Int): Int
	fun BASS_MusicLoad(memory: Boolean, file: WString, offset: Long, length: Int, flags: Int, frequency: Int): Int

	// errors: Handle
	fun BASS_MusicFree(handle: Int): Boolean

	open class BASS_PLUGININFO private constructor(): Structure() {
		class ByReference: BASS_PLUGININFO(), Structure.ByReference

		@JvmField
		var version: Int = 0

		@JvmField
		var formatc: Int = 0

		@JvmField
		var formats: Array<BASS_PLUGINFORM.ByReference> = emptyArray()

		override fun getFieldOrder(): MutableList<String> {
			return mutableListOf("version", "formatc", "formats")
		}
	}

	open class BASS_PLUGINFORM private constructor(): Structure() {
		class ByReference: BASS_PLUGINFORM(), Structure.ByReference

		@JvmField
		var ctype: Int = 0

		@JvmField
		var name: String = ""

		@JvmField
		var exts: String = ""

		override fun getFieldOrder(): MutableList<String> {
			return mutableListOf("ctype", "name", "exts")
		}
	}

	open class BASS_DEVICEINFO: Structure() {
		@JvmField
		var description: Pointer? = null

		@JvmField
		var driver: Pointer? = null

		val stringDescription: String
			get() {
				val pointer = description!!
				return if (instance.BASS_GetConfig(C.BASS_CONFIG_UNICODE) == 1)
					pointer.getWideString(0)
				else
					pointer.getString(0)
			}

		val stringDriver: String?
			get() {
				val pointer = description ?: return null
				return if (instance.BASS_GetConfig(C.BASS_CONFIG_UNICODE) == 1)
					pointer.getWideString(0)
				else
					pointer.getString(0)
			}

		override fun getFieldOrder(): MutableList<String> {
			return mutableListOf("description", "driver")
		}
	}

	open class BASS_INFO private constructor(): Structure() {
		class ByReference: BASS_INFO(), Structure.ByReference

		@JvmField
		var flags: Int = 0

		@JvmField
		var hwsize: Int = 0

		@JvmField
		var hwfree: Int = 0

		@JvmField
		var freesam: Int = 0

		@JvmField
		var free3d: Int = 0

		@JvmField
		var minrate: Int = 0

		@JvmField
		var maxrate: Int = 0

		@JvmField
		var eax: Boolean = false

		@JvmField
		var minbuf: Int = 0

		@JvmField
		var dsver: Int = 0

		@JvmField
		var latency: Int = 0

		@JvmField
		var initflags: Int = 0

		@JvmField
		var speakers: Int = 0

		@JvmField
		var freq: Int = 0

		override fun getFieldOrder(): MutableList<String> {
			return mutableListOf(
					"flags", "hwsize", "hwfree", "freesam", "free3d", "minrate", "maxrate", "eax",
					"minbuf", "dsver", "latency", "initflags", "speakers", "freq"
			)
		}
	}

	open class BASS_CHANNELINFO private constructor(): Structure() {
		class ByReference: BASS_CHANNELINFO(), Structure.ByReference

		@JvmField
		var freq: Int = 0

		@JvmField
		var chans: Int = 0

		@JvmField
		var flags: Int = 0

		@JvmField
		var ctype: Int = 0

		@JvmField
		var origres: Int = 0

		@JvmField
		var plugin: Int = 0

		@JvmField
		var sample: Int = 0

		@JvmField
		var filename: String? = null

		override fun getFieldOrder(): MutableList<String> {
			return mutableListOf("freq", "chans", "flags", "ctype", "origres", "plugin", "sample", "filename")
		}
	}

	interface STREAMPROC: Callback {
		fun invoke(handle: Int, buffer: Pointer, length: Int, userData: Pointer?): Int
	}

	interface DOWNLOADPROC: Callback {
		fun invoke(buffer: Pointer?, length: Int, userData: Pointer?)
	}

	interface SYNCPROC: Callback {
		fun invoke(handle: Int, channel: Int, data: Int, userData: Pointer?)
	}
}