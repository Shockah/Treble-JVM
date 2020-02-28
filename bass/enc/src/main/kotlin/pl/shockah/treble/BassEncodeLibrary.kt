package pl.shockah.treble

object BassEncodeLibrary {
	internal val baseLib: BassNativeLibrary
		get() = BassNativeLibrary.instance

	internal val lib: BassEncodeNativeLibrary
		get() = BassEncodeNativeLibrary.instance

	internal val encoders = mutableSetOf<BassEncoder>()

	val version: String
		get() {
			val result = lib.BASS_Encode_GetVersion()
			return listOf(result ushr 24 and 0xFF, result ushr 16 and 0xFF, result ushr 8 and 0xFF, result and 0xFF).joinToString(".") { "$it" }
		}

	fun throwErrorExceptionIfAny() {
		when (val errorCode = baseLib.BASS_ErrorGetCode()) {
			0 -> { }
			else -> throwErrorException(errorCode)
		}
	}

	fun throwErrorException(errorCode: Int = baseLib.BASS_ErrorGetCode()): Nothing {
		throw BassEncodeException.buildFromErrorCode(errorCode)
	}
}