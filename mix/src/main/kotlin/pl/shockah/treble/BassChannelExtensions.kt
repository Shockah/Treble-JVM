package pl.shockah.treble

val BassChannel.mixer: BassMixer?
	get() = BassMixerLibrary.mixers.firstOrNull { this in it.channels }