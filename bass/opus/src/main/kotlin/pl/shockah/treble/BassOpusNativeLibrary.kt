@file:Suppress("FunctionName")

package pl.shockah.treble

import com.sun.jna.*
import java.nio.ByteBuffer
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

interface BassOpusNativeLibrary: Library {
	companion object {
		internal const val libName = "bassopus"

		private var internalInstance: BassOpusNativeLibrary? = null

		private val lock = ReentrantLock()

		private var plugin: BassPlugin? = null

		val instance: BassOpusNativeLibrary
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
				plugin = BassLibrary.loadPlugin(libName)
				internalInstance = Native.synchronizedLibrary(Native.load(libName, BassOpusNativeLibrary::class.java)) as BassOpusNativeLibrary
			}
		}

		fun unloadLibrary() {
			lock.withLock {
				internalInstance?.let {
					plugin?.free()
					NativeLibrary.getInstance(libName).dispose()
					internalInstance = null
				}
			}
		}
	}

	// errors: Init, NotAvailable, IllegalParameter, FileOpen, FileFormat, Speaker, Memory, No3D, Unknown
	fun BASS_OPUS_StreamCreateFile(memory: Boolean, file: ByteBuffer, offset: Long, length: Long, flags: Int): Int
	fun BASS_OPUS_StreamCreateFile(memory: Boolean, file: String, offset: Long, length: Long, flags: Int): Int
	fun BASS_OPUS_StreamCreateFile(memory: Boolean, file: WString, offset: Long, length: Long, flags: Int): Int

	// errors: Init, NotAvailable, NoNetwork, IllegalParameter, Ssl, Timeout, FileOpen, FileFormat, Codec, Format, Speaker, Memory, No3D, Unknown
	fun BASS_OPUS_StreamCreateURL(url: String, offset: Int, flags: Int, processor: BassNativeLibrary.DOWNLOADPROC?, userData: Pointer?): Int
	fun BASS_OPUS_StreamCreateURL(url: WString, offset: Int, flags: Int, processor: BassNativeLibrary.DOWNLOADPROC?, userData: Pointer?): Int
}