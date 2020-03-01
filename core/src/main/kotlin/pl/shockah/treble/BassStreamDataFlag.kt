package pl.shockah.treble

import pl.shockah.treble.BassNativeConstants as C

interface BassStreamDataFlag: BassFlag {
	class Impl internal constructor(
			override val nativeValue: Int
	): BassStreamDataFlag

	operator fun plus(flag: BassStreamDataFlag): BassStreamDataFlag {
		return Impl(nativeValue or flag.nativeValue)
	}

	companion object {
		val None = Impl(0)

		val FloatData = Impl(C.BASS_DATA_FLOAT)
		val FixedPoint = Impl(C.BASS_DATA_FIXED) // only available on Android and Windows CE
		val Fft256 = Impl(C.BASS_DATA_FFT256.toInt())
		val Fft512 = Impl(C.BASS_DATA_FFT512.toInt())
		val Fft1024 = Impl(C.BASS_DATA_FFT1024.toInt())
		val Fft2048 = Impl(C.BASS_DATA_FFT2048.toInt())
		val Fft4096 = Impl(C.BASS_DATA_FFT4096.toInt())
		val Fft8192 = Impl(C.BASS_DATA_FFT8192.toInt())
		val Fft16384 = Impl(C.BASS_DATA_FFT16384.toInt())
		val Fft32768 = Impl(C.BASS_DATA_FFT32768.toInt())
		val ComplexFft = Impl(C.BASS_DATA_FFT_COMPLEX)
		val IndividualFft = Impl(C.BASS_DATA_FFT_INDIVIDUAL)
		val NoHannWindowFft = Impl(C.BASS_DATA_FFT_NOWINDOW)
		val IncludeNyquistFft = Impl(C.BASS_DATA_FFT_NYQUIST)
		val RemoveDcBiasFft = Impl(C.BASS_DATA_FFT_REMOVEDC)
	}
}