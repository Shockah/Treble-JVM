package pl.shockah.treble

import pl.shockah.treble.BassMixerLibrary as L

fun BassDevice.createMixer(frequency: Int? = null, channels: Int? = null, flags: BassMixerFlag = BassMixerFlag.None): BassMixer {
	return newChannel {
		setAsCurrent()
		val result = L.lib.BASS_Mixer_StreamCreate(frequency ?: this.frequency, channels ?: info.availableSpeakers.takeIf { it > 0 } ?: 2, flags.nativeValue)
		if (result == 0)
			L.throwErrorException()
		val mixer = BassMixer(result, this, BassMixerFlag.FreeAutomatically in flags)
		L.mixers += mixer
		return@newChannel mixer
	}
}