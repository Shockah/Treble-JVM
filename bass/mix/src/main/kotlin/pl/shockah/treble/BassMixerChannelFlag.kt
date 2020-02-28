package pl.shockah.treble

import pl.shockah.treble.BassMixerNativeConstants as C
import pl.shockah.treble.BassNativeConstants as BC

interface BassMixerChannelFlag: BassFlag {
	open class Impl internal constructor(
			override val nativeValue: Int
	): BassMixerChannelFlag

	operator fun plus(flag: BassMixerChannelFlag): BassMixerChannelFlag {
		return Impl(nativeValue or flag.nativeValue)
	}

	operator fun plus(flag: BassSpeakerFlag): BassMixerChannelFlag {
		return Impl(nativeValue or flag.nativeValue)
	}

	companion object {
		val None = Impl(0)

		val ChannelMatrix = Impl(C.BASS_MIXER_MATRIX)
		val Downmix = Impl(C.BASS_MIXER_DOWNMIX)
		val BufferData = Impl(C.BASS_MIXER_BUFFER)
		val Limit = Impl(C.BASS_MIXER_LIMIT)
		val NoRampIn = Impl(C.BASS_MIXER_NORAMPIN)
		val Paused = Impl(C.BASS_MIXER_PAUSE)
		val FreeAutomatically = Impl(BC.BASS_STREAM_AUTOFREE)

		class Speaker(
				val flag: BassSpeakerFlag
		): Impl(flag.nativeValue)
	}
}