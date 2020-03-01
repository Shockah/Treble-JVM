package pl.shockah.treble

import pl.shockah.treble.BassLibrary as L
import pl.shockah.treble.BassNativeConstants as C

open class BassBufferStream(
		handle: Int,
		initialDevice: BassDevice,
		autoFree: Boolean
): BassStream(handle, initialDevice, autoFree) {
	val asyncBufferLength: Int
		get() = libGetFilePosition(C.BASS_FILEPOS_ASYNCBUF).toInt()

	val bufferLength: Int
		get() = libGetFilePosition(C.BASS_FILEPOS_BUFFER).toInt()

	val bufferingBeforePlaybackProgress: Float
		get() = libGetFilePosition(C.BASS_FILEPOS_BUFFERING).toInt() / 100f

	val isConnected: Boolean
		get() = libGetFilePosition(C.BASS_FILEPOS_CONNECTED) != 0L

	val dataPosition: Int
		get() = libGetFilePosition(C.BASS_FILEPOS_CURRENT).toInt()

	val downloadProgress: Int
		get() = libGetFilePosition(C.BASS_FILEPOS_DOWNLOAD).toInt()

	val dataLength: Int
		get() = libGetFilePosition(C.BASS_FILEPOS_END).toInt()

	val bufferSize: Int
		get() = libGetFilePosition(C.BASS_FILEPOS_SIZE).toInt()

	val audioDataStartPosition: Int
		get() = libGetFilePosition(C.BASS_FILEPOS_START).toInt()

	private fun libGetFilePosition(mode: Int): Long {
		if (freed)
			throw IllegalStateException()

		val result = L.lib.BASS_StreamGetFilePosition(handle, mode)
		if (result == -1L)
			L.throwErrorException()
		return result
	}
}