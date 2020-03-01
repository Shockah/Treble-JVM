package pl.shockah.treble

import pl.shockah.treble.BassNativeConstants as C

interface BassInitFlag: BassFlag {
	class Impl internal constructor(
			override val nativeValue: Int
	): BassInitFlag

	operator fun plus(flag: BassInitFlag): BassInitFlag {
		return Impl(nativeValue or flag.nativeValue)
	}

	companion object {
		val None = Impl(0)

		val Use8Bits = Impl(C.BASS_DEVICE_8BITS)
		val Mono = Impl(C.BASS_DEVICE_8BITS)
		val Stereo = Impl(C.BASS_DEVICE_8BITS)
		val Enable3D = Impl(C.BASS_DEVICE_8BITS)
		val CalculateLatency = Impl(C.BASS_DEVICE_8BITS)
		val UseWindowsControlPanelSpeakerSetup = Impl(C.BASS_DEVICE_8BITS)
		val ForceSpeakerAssignment = Impl(C.BASS_DEVICE_8BITS)
		val NoSpeakerSetup = Impl(C.BASS_DEVICE_8BITS)
		val UseWindowsDirectSound = Impl(C.BASS_DEVICE_8BITS)
		val UseAndroidAudioTrack = Impl(C.BASS_DEVICE_8BITS)
		val UseLinuxAlsaDmix = Impl(C.BASS_DEVICE_8BITS)
	}
}