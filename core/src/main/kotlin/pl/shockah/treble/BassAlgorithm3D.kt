package pl.shockah.treble

import pl.shockah.treble.BassNativeConstants as C

enum class BassAlgorithm3D(
		override val nativeValue: Int
): BassFlag {
	Default(C.BASS_3DALG_DEFAULT),
	Off(C.BASS_3DALG_OFF),
	Full(C.BASS_3DALG_FULL),
	Light(C.BASS_3DALG_LIGHT)
}