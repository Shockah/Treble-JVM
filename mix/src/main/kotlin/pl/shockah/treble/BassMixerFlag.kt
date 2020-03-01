package pl.shockah.treble

import pl.shockah.treble.BassMixerNativeConstants as C
import pl.shockah.treble.BassNativeConstants as BC

interface BassMixerFlag: BassFlag {
	open class Impl internal constructor(
			override val nativeValue: Int
	): BassMixerFlag

	operator fun plus(flag: BassMixerFlag): BassMixerFlag {
		return Impl(nativeValue or flag.nativeValue)
	}

	operator fun plus(flag: BassSpeakerFlag): BassMixerFlag {
		return Impl(nativeValue or flag.nativeValue)
	}

	companion object {
		val None = Impl(0)

		val Use8Bits = Impl(BC.BASS_SAMPLE_8BITS)
		val UseFloatData = Impl(BC.BASS_SAMPLE_FLOAT)
		val UseSoftwareMixing = Impl(BC.BASS_SAMPLE_SOFTWARE)
		val Enable3D = Impl(BC.BASS_SAMPLE_3D)
		val EnableDirectXEffects = Impl(BC.BASS_SAMPLE_FX)
		val FreeAutomatically = Impl(BC.BASS_STREAM_AUTOFREE)
		val DecodeOnly = Impl(BC.BASS_STREAM_DECODE)
		val EndOnNoActiveChannels = Impl(C.BASS_MIXER_END)
		val ProduceSilence = Impl(C.BASS_MIXER_NONSTOP)
		val KeepSourcePositions = Impl(C.BASS_MIXER_POSEX)
		val ResumeImmediatelyWhenStalled = Impl(C.BASS_MIXER_RESUME)

		class Speaker(
				val flag: BassSpeakerFlag
		): Impl(flag.nativeValue)
	}
}