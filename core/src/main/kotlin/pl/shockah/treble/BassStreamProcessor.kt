package pl.shockah.treble

import pl.shockah.treble.BassNativeConstants as C

enum class BassStreamProcessor(
		override val nativeValue: Int
): BassFlag {
	Dummy(C.STREAMPROC_DUMMY),
	Device(C.STREAMPROC_DEVICE),
	Device3D(C.STREAMPROC_DEVICE_3D)
}