package pl.shockah.treble

import pl.shockah.treble.BassNativeConstants as C

interface BassMusicFlag: BassFlag {
	open class Impl internal constructor(
			override val nativeValue: Int
	): BassMusicFlag

	operator fun plus(flag: BassMusicFlag): BassMusicFlag {
		return Impl(nativeValue or flag.nativeValue)
	}

	operator fun plus(flag: BassSpeakerFlag): BassMusicFlag {
		return Impl(nativeValue or flag.nativeValue)
	}

	companion object {
		val None = Impl(0)

		val Use8Bits = Impl(C.BASS_SAMPLE_8BITS)
		val UseFloatData = Impl(C.BASS_SAMPLE_FLOAT)
		val Mono = Impl(C.BASS_SAMPLE_MONO)
		val UseSoftwareMixing = Impl(C.BASS_SAMPLE_SOFTWARE)
		val Enable3D = Impl(C.BASS_SAMPLE_3D)
		val EnableDirectXEffects = Impl(C.BASS_SAMPLE_FX)
		val Loop = Impl(C.BASS_SAMPLE_LOOP)
		val NonInterpolatedMixing = Impl(C.BASS_MUSIC_NONINTER)
		val SincInterpolatedMixing = Impl(C.BASS_MUSIC_SINCINTER)
		val NormalRamping = Impl(C.BASS_MUSIC_RAMP)
		val SensitiveRamping = Impl(C.BASS_MUSIC_RAMPS)
		val Surround = Impl(C.BASS_MUSIC_SURROUND)
		val AlternativeSurround = Impl(C.BASS_MUSIC_SURROUND2)
		val FastTracker2Panning = Impl(C.BASS_MUSIC_FT2PAN)
		val FastTracker2Playback = Impl(C.BASS_MUSIC_FT2MOD)
		val ProTracker1Playback = Impl(C.BASS_MUSIC_PT1MOD)
		val StopNotesOnSeek = Impl(C.BASS_MUSIC_POSRESET)
		val StopNotesAndResetOnSeek = Impl(C.BASS_MUSIC_POSRESETEX)
		val StopOnBackwardsJump = Impl(C.BASS_MUSIC_STOPBACK)
		val PrescanLength = Impl(C.BASS_MUSIC_PRESCAN)
		val DontLoadSamples = Impl(C.BASS_MUSIC_NOSAMPLE)
		val FreeAutomatically = Impl(C.BASS_MUSIC_AUTOFREE)
		val DecodeOnly = Impl(C.BASS_MUSIC_DECODE)

		class Speaker(
				val flag: BassSpeakerFlag
		): Impl(flag.nativeValue)
	}
}