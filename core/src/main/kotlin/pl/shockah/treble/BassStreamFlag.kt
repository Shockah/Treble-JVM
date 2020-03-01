package pl.shockah.treble

import pl.shockah.treble.BassNativeConstants as C

interface BassStreamFlag: BassFlag {
	open class Impl internal constructor(
			override val nativeValue: Int
	): BassStreamFlag

	operator fun plus(flag: BassStreamFlag): BassStreamFlag {
		return Impl(nativeValue or flag.nativeValue)
	}

	operator fun plus(flag: BassSpeakerFlag): BassStreamFlag {
		return Impl(nativeValue or flag.nativeValue)
	}

	companion object {
		val None = Impl(0)

		val Use8Bits = Impl(C.BASS_SAMPLE_8BITS)
		val UseFloatData = Impl(C.BASS_SAMPLE_FLOAT)
		val UseSoftwareMixing = Impl(C.BASS_SAMPLE_SOFTWARE)
		val Enable3D = Impl(C.BASS_SAMPLE_3D)
		val EnableDirectXEffects = Impl(C.BASS_SAMPLE_FX)
		val FreeAutomatically = Impl(C.BASS_STREAM_AUTOFREE)
		val DecodeOnly = Impl(C.BASS_STREAM_DECODE)

		class Speaker(
				val flag: BassSpeakerFlag
		): Impl(flag.nativeValue)
	}
}