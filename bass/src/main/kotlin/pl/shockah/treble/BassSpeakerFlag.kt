package pl.shockah.treble

import pl.shockah.treble.BassNativeConstants as C

interface BassSpeakerFlag: BassFlag {
	open class Impl internal constructor(
			override val nativeValue: Int
	): BassSpeakerFlag

	operator fun plus(flag: BassSpeakerFlag): BassSpeakerFlag {
		return Impl(nativeValue or flag.nativeValue)
	}

	companion object {
		val None = Impl(0)

		val Front = Impl(C.BASS_SPEAKER_FRONT)
		val SideOrRear = Impl(C.BASS_SPEAKER_REAR)
		val CenterAndSubwooferIn5Point1 = Impl(C.BASS_SPEAKER_CENLFE)
		val RearIn7Point1 = Impl(C.BASS_SPEAKER_REAR2)

		val FrontLeft = Impl(C.BASS_SPEAKER_FRONTLEFT)
		val FrontRight = Impl(C.BASS_SPEAKER_FRONTRIGHT)
		val RearLeft = Impl(C.BASS_SPEAKER_REARLEFT)
		val RearRight = Impl(C.BASS_SPEAKER_REARRIGHT)
		val CenterIn5Point1 = Impl(C.BASS_SPEAKER_CENTER)
		val Subwoofer = Impl(C.BASS_SPEAKER_LFE)
		val RearLeftCenterIn7Point1 = Impl(C.BASS_SPEAKER_REAR2LEFT)
		val RearRightCenterIn7Point1 = Impl(C.BASS_SPEAKER_REAR2RIGHT)

		data class NthSpeaker(
				val n: Int
		): Impl(n shl 24)
	}
}