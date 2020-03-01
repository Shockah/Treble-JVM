package pl.shockah.treble

object BassTagsLibrary {
	internal val baseLib: BassNativeLibrary
		get() = BassNativeLibrary.instance

	internal val lib: BassTagsNativeLibrary
		get() = BassTagsNativeLibrary.instance

	val version: String
		get() {
			val result = lib.TAGS_GetVersion()
			return listOf(result ushr 24 and 0xFF, result ushr 16 and 0xFF, result ushr 8 and 0xFF, result and 0xFF).joinToString(".") { "$it" }
		}

	init {
		lib.TAGS_SetUTF8(true)
	}
}