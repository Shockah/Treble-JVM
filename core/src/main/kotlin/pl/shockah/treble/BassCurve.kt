package pl.shockah.treble

enum class BassCurve(
		override val nativeValue: Int
): BassFlag {
	Linear(0),
	Logarithmic(1)
}