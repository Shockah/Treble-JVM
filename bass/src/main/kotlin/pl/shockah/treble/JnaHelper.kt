package pl.shockah.treble

import com.sun.jna.Native
import com.sun.jna.NativeLibrary
import java.io.File
import java.io.IOException
import java.lang.reflect.Method

object JnaHelper {
	private val mapSharedLibraryNameMethod: Method by lazy {
		val method = NativeLibrary::class.java.getDeclaredMethod("mapSharedLibraryName", String::class.java)
		method.isAccessible = true
		return@lazy method
	}

	fun extractLibrary(name: String): File {
		val extracted = Native.extractFromResourcePath(name)
		val sharedLibraryName = mapSharedLibraryNameMethod(null, name) as String

		if (extracted.name == sharedLibraryName)
			return extracted

		val newFile = File(extracted.parentFile, sharedLibraryName)
		try {
			if (newFile.exists())
				newFile.delete()
			extracted.renameTo(newFile)
			newFile.deleteOnExit()
		} catch (_: IOException) {
		}
		return newFile
	}
}