package pl.shockah.treble

import pl.shockah.treble.BassLibrary as L

class BassSynchronizer<T: BassSyncEvent>(
		val handle: Int,
		val channel: BassChannel,
		val type: BassSyncEvent.Type<T>
) {
	var freed = false
		private set

	protected fun finalize() {
		free()
	}

	internal fun free(freedAutomatically: Boolean) {
		if (freed)
			return

		freed = true
		if (!freedAutomatically) {
			if (!L.lib.BASS_ChannelRemoveSync(channel.handle, handle))
				L.throwErrorException()
		}
		L.synchronizers -= this
	}

	fun free() {
		free(false)
	}
}