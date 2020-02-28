package pl.shockah.treble

import java.nio.ByteBuffer
import pl.shockah.treble.BassLibrary as L
import pl.shockah.treble.BassNativeConstants as C

class BassPushStream(
		handle: Int,
		initialDevice: BassDevice,
		autoFree: Boolean
): BassStream(handle, initialDevice, autoFree) {
	fun signalStreamEnd() {
		if (L.lib.BASS_StreamPutData(handle, null, C.BASS_STREAMPROC_END.toInt()) == -1)
			L.throwErrorException()
	}

	fun putData(buffer: ByteBuffer) {
		if (L.lib.BASS_StreamPutData(handle, buffer, buffer.remaining()) == -1)
			L.throwErrorException()
	}
}