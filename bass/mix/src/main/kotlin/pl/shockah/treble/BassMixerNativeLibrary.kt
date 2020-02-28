@file:Suppress("FunctionName")

package pl.shockah.treble

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.NativeLibrary
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

interface BassMixerNativeLibrary: Library {
	companion object {
		internal const val libName = "bassmix"

		private var internalInstance: BassMixerNativeLibrary? = null

		private val lock = ReentrantLock()

		val instance: BassMixerNativeLibrary
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
				internalInstance = Native.synchronizedLibrary(Native.load(libName, BassMixerNativeLibrary::class.java)) as BassMixerNativeLibrary
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

	fun BASS_Mixer_GetVersion(): Int

	// errors: Init, NotAvailable, Format, Speaker, Memory, No3D, Unknown
	fun BASS_Mixer_StreamCreate(frequency: Int, channels: Int, flags: Int): Int

	// errors: Handle, Decode, Already, Speaker
	fun BASS_Mixer_StreamAddChannel(handle: Int, channel: Int, flags: Int): Boolean

	// errors: Handle
	fun BASS_Mixer_ChannelRemove(handle: Int): Boolean
}