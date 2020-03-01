@file:Suppress("FunctionName")

package pl.shockah.treble

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.NativeLibrary
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

interface BassTagsNativeLibrary: Library {
	companion object {
		internal const val libName = "tags"

		private var internalInstance: BassTagsNativeLibrary? = null

		private val lock = ReentrantLock()

		val instance: BassTagsNativeLibrary
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
				internalInstance = Native.synchronizedLibrary(Native.load(libName, BassTagsNativeLibrary::class.java)) as BassTagsNativeLibrary
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

	fun TAGS_GetVersion(): Int

	fun TAGS_SetUTF8(enable: Boolean): Boolean

	fun TAGS_Read(handle: Int, format: String): String

	fun TAGS_ReadEx(handle: Int, format: String, tagType: Int, codePage: Int): String
}