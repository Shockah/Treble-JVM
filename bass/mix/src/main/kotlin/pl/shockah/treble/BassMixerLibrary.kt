package pl.shockah.treble

object BassMixerLibrary {
	internal val baseLib: BassNativeLibrary
		get() = BassNativeLibrary.instance

	internal val lib: BassMixerNativeLibrary
		get() = BassMixerNativeLibrary.instance

	internal val mixers = mutableSetOf<BassMixer>()

	val version: String
		get() {
			val result = lib.BASS_Mixer_GetVersion()
			return listOf(result ushr 24 and 0xFF, result ushr 16 and 0xFF, result ushr 8 and 0xFF, result and 0xFF).joinToString(".") { "$it" }
		}

	fun throwErrorExceptionIfAny() {
		BassLibrary.throwErrorExceptionIfAny()
	}

	fun throwErrorException(errorCode: Int = baseLib.BASS_ErrorGetCode()): Nothing {
		BassLibrary.throwErrorException(errorCode)
	}
}