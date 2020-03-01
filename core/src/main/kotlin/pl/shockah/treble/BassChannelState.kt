package pl.shockah.treble

import pl.shockah.treble.BassNativeConstants as C

enum class BassChannelState(
		override val nativeValue: Int
): BassFlag {
	Stopped(C.BASS_ACTIVE_STOPPED),
	Playing(C.BASS_ACTIVE_PLAYING),
	Paused(C.BASS_ACTIVE_PAUSED),
	DevicePaused(C.BASS_ACTIVE_PAUSED_DEVICE),
	Stalled(C.BASS_ACTIVE_STALLED)
}