package pl.shockah.treble

class BassPlugin(
		val handle: Int,
		val name: String
) {
	private var freed = false

	protected fun finalize() {
		free()
	}

	fun free() {
		if (freed)
			return

		freed = true
		if (!BassLibrary.lib.BASS_StreamFree(handle))
			BassLibrary.throwErrorException()
		BassLibrary.internalPlugins -= this
	}
}