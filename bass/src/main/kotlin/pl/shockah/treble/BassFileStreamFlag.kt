package pl.shockah.treble

import pl.shockah.treble.BassNativeConstants as C

interface BassFileStreamFlag: BassFlag {
	open class Impl internal constructor(
			override val nativeValue: Int
	): BassFileStreamFlag

	operator fun plus(flag: BassFileStreamFlag): BassFileStreamFlag {
		return Impl(nativeValue or flag.nativeValue)
	}

	operator fun plus(flag: BassSpeakerFlag): BassFileStreamFlag {
		return Impl(nativeValue or flag.nativeValue)
	}

	companion object {
		val None = Impl(0)

		val UseFloatData = Impl(C.BASS_SAMPLE_FLOAT)
		val Mono = Impl(C.BASS_SAMPLE_MONO)
		val UseSoftwareMixing = Impl(C.BASS_SAMPLE_SOFTWARE)
		val Enable3D = Impl(C.BASS_SAMPLE_3D)
		val Loop = Impl(C.BASS_SAMPLE_LOOP)
		val EnableDirectXEffects = Impl(C.BASS_SAMPLE_FX)
		val PreScanSeekPointsAndLength = Impl(C.BASS_STREAM_PRESCAN)
		val FreeAutomatically = Impl(C.BASS_STREAM_AUTOFREE)
		val DecodeOnly = Impl(C.BASS_STREAM_DECODE)
		val Async = Impl(C.BASS_ASYNCFILE)

		class Speaker(
				val flag: BassSpeakerFlag
		): Impl(flag.nativeValue)
	}
}