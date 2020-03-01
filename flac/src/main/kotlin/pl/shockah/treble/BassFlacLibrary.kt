package pl.shockah.treble

object BassFlacLibrary {
	internal val baseLib: BassNativeLibrary
		get() = BassNativeLibrary.instance

	internal val lib: BassFlacNativeLibrary
		get() = BassFlacNativeLibrary.instance
}