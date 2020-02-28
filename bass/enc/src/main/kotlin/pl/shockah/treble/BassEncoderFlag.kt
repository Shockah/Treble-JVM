package pl.shockah.treble

import pl.shockah.treble.BassEncodeNativeConstants as C

interface BassEncoderFlag: BassFlag {
	class Impl internal constructor(
			override val nativeValue: Int
	): BassEncoderFlag

	operator fun plus(flag: BassEncoderFlag): BassEncoderFlag {
		return Impl(nativeValue or flag.nativeValue)
	}

	companion object {
		val None = Impl(0)

		val AiffHeader = Impl(C.BASS_ENCODE_AIFF)
		val NoHeader = Impl(C.BASS_ENCODE_NOHEAD)
		val Rf64Header = Impl(C.BASS_ENCODE_RF64)
		val WaveFormatExtensible = Impl(C.BASS_ENCODE_WFEXT)
		val BigEndian = Impl(C.BASS_ENCODE_BIGEND)
		val Float8Bit = Impl(C.BASS_ENCODE_FP_8BIT)
		val Float16Bit = Impl(C.BASS_ENCODE_FP_16BIT)
		val Float24Bit = Impl(C.BASS_ENCODE_FP_24BIT)
		val Float32Bit = Impl(C.BASS_ENCODE_FP_32BIT)
		val FloatAuto = Impl(C.BASS_ENCODE_FP_AUTO)
		val Dither = Impl(C.BASS_ENCODE_DITHER)
		val Queue = Impl(C.BASS_ENCODE_QUEUE)
		val LimitToRealTimeSpeed = Impl(C.BASS_ENCODE_LIMIT)
		val DontLimitWhenCasting = Impl(C.BASS_ENCODE_CAST_NOLIMIT)
		val StartPaused = Impl(C.BASS_ENCODE_PAUSE)
		val FreeAutomatically = Impl(C.BASS_ENCODE_AUTOFREE)
	}
}