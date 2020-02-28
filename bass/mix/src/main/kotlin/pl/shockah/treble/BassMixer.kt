package pl.shockah.treble

import pl.shockah.treble.BassMixerLibrary as L

class BassMixer(
		handle: Int,
		initialDevice: BassDevice,
		autoFree: Boolean
): BassStream(handle, initialDevice, autoFree) {
	internal val channels = mutableListOf<BassChannel>()

	override fun free(autoFree: Boolean) {
		super.free(autoFree)
		L.mixers -= this
	}

	fun addChannel(channel: BassChannel, flags: BassMixerChannelFlag = BassMixerChannelFlag.None) {
		if (!L.lib.BASS_Mixer_StreamAddChannel(handle, channel.handle, flags.nativeValue))
			L.throwErrorException()
		channels += channel
	}

	fun removeChannel(channel: BassChannel) {
		if (channel !in channels)
			return
		if (!channel.freed) {
			if (!L.lib.BASS_Mixer_ChannelRemove(channel.handle))
				L.throwErrorException()
		}
		channels -= channel
	}
}