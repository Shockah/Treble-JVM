package pl.shockah.treble

object BassOpusLibrary {
	internal val baseLib: BassNativeLibrary
		get() = BassNativeLibrary.instance

	internal val lib: BassOpusNativeLibrary
		get() = BassOpusNativeLibrary.instance
}