package pl.shockah.treble

import pl.shockah.treble.BassLibrary as L
import pl.shockah.treble.BassNativeConstants as C

open class BassMusic(
		handle: Int,
		initialDevice: BassDevice,
		autoFree: Boolean
): BassChannel(handle, initialDevice, autoFree) {
	/** Returns duration of file in milliseconds */
	val duration: Long
		get() {
			if (freed)
				throw IllegalStateException()

			val length = L.lib.BASS_ChannelGetLength(handle, C.BASS_POS_BYTE)
			if (length == -1L)
				L.throwErrorException()

			val time = L.lib.BASS_ChannelBytes2Seconds(handle, length)
			if (time < 0)
				L.throwErrorException()

			return (time * 1000.0).toLong()
		}

	override fun free(autoFree: Boolean) {
		if (freed)
			return

		freed = true
		if (!autoFree) {
			if (!L.lib.BASS_MusicFree(handle))
				L.throwErrorException()
		}
		L.synchronizers.removeAll { it.channel == this }
		L.channels -= this
		if (!L.channels.any { it.device == device })
			device.free()
	}
}