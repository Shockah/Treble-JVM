package pl.shockah.treble

import pl.shockah.treble.BassNativeConstants as C

interface BassChannelTypeFlag: BassFlag {
	open class Impl internal constructor(
			override val nativeValue: Int
	): BassChannelTypeFlag

	operator fun plus(flag: BassChannelTypeFlag): BassChannelTypeFlag {
		return Impl(nativeValue or flag.nativeValue)
	}

	operator fun plus(flag: BassSpeakerFlag): BassChannelTypeFlag {
		return Impl(nativeValue or flag.nativeValue)
	}

	companion object {
		val None = Impl(0)

		val Use8Bits = Impl(C.BASS_SAMPLE_8BITS)
		val UseFloatData = Impl(C.BASS_SAMPLE_FLOAT)
		val Loop = Impl(C.BASS_SAMPLE_LOOP)
		val Enable3D = Impl(C.BASS_SAMPLE_3D)
		val UseSoftwareMixing = Impl(C.BASS_SAMPLE_SOFTWARE)
		val DirectX7VoiceAllocationAndManagement = Impl(C.BASS_SAMPLE_VAM)
		val MuteAtMaxDistance = Impl(C.BASS_SAMPLE_MUTEMAX)
		val EnableDirectXEffects = Impl(C.BASS_SAMPLE_FX)
		val DownloadRateRestricted = Impl(C.BASS_STREAM_RESTRATE)
		val BufferedInBlocks = Impl(C.BASS_STREAM_BLOCK)
		val FreeAutomatically = Impl(C.BASS_STREAM_AUTOFREE)
		val DecodeOnly = Impl(C.BASS_STREAM_DECODE)
		val NormalRamping = Impl(C.BASS_MUSIC_RAMP)
		val SensitiveRamping = Impl(C.BASS_MUSIC_RAMPS)
		val Surround = Impl(C.BASS_MUSIC_SURROUND)
		val AlternativeSurround = Impl(C.BASS_MUSIC_SURROUND2)
		val NonInterpolatedMixing = Impl(C.BASS_MUSIC_NONINTER)
		val FastTracker2Playback = Impl(C.BASS_MUSIC_FT2MOD)
		val ProTracker1Playback = Impl(C.BASS_MUSIC_PT1MOD)
		val Async = Impl(C.BASS_ASYNCFILE)

		class Speaker(
				val flag: BassSpeakerFlag
		): Impl(flag.nativeValue)
	}
}