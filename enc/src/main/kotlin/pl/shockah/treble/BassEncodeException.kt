package pl.shockah.treble

import pl.shockah.treble.BassEncodeNativeConstants as C

open class BassEncodeException: BassException() {
	companion object {
		fun buildFromErrorCode(code: Int): BassException {
			return when (code) {
				C.BASS_ERROR_ACM_CANCEL -> AcmCancel()
				C.BASS_ERROR_CAST_DENIED -> CastDenied()
				else -> BassException.buildFromErrorCode(code)
			}
		}
	}

	class AcmCancel: BassEncodeException()

	class CastDenied: BassEncodeException()
}