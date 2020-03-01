@file:Suppress("FunctionName")

package pl.shockah.treble

import com.sun.jna.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

interface BassEncodeNativeLibrary: Library {
	companion object {
		internal const val libName = "bassenc"

		private var internalInstance: BassEncodeNativeLibrary? = null

		private val lock = ReentrantLock()

		val instance: BassEncodeNativeLibrary
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
				BassNativeLibrary.loadLibrary()
				val lib = JnaHelper.extractLibrary(libName)
				System.load(lib.absoluteFile.normalize().absolutePath)
				internalInstance = Native.synchronizedLibrary(Native.load(libName, BassEncodeNativeLibrary::class.java)) as BassEncodeNativeLibrary
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

	fun BASS_Encode_GetVersion(): Int

	// errors: Handle
	fun BASS_Encode_GetChannel(handle: Int): Int

	// errors: Handle, Format
	fun BASS_Encode_SetChannel(handle: Int, channel: Int): Boolean

	// errors: Handle, FileOpen, Create, NotAvailable, Unknown
	fun BASS_Encode_Start(handle: Int, commandLine: String?, flags: Int, proc: ENCODEPROC?, userData: Pointer?): Int
	fun BASS_Encode_Start(handle: Int, commandLine: WString?, flags: Int, proc: ENCODEPROC?, userData: Pointer?): Int

	// errors: Handle
	fun BASS_Encode_Stop(handle: Int): Boolean

	// errors: Handle, Already, IllegalParameter, Busy, Memory, Unknown
	fun BASS_Encode_ServerInit(handle: Int, port: String, buffer: Int, burst: Int, flags: Int, proc: ENCODECLIENTPROC?, userData: Pointer?): Int

	// errors: Handle, NotAvailable
	fun BASS_Encode_ServerKick(handle: Int, client: String): Int

	interface ENCODEPROC: Callback {
		fun invoke(handle: Int, channel: Int, buffer: Pointer, length: Int, userData: Pointer?)
	}

	interface ENCODECLIENTPROC: Callback {
		fun invoke(handle: Int, connect: Boolean, client: String, headers: StringArray, userData: Pointer?): Boolean
	}
}