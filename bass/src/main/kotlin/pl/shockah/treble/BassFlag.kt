package pl.shockah.treble

interface BassFlag {
	val nativeValue: Int

	data class Impl(
			override val nativeValue: Int
	): BassFlag

	operator fun plus(flag: BassFlag): BassFlag {
		return Impl(nativeValue or flag.nativeValue)
	}

	operator fun contains(flag: BassFlag): Boolean {
		return (nativeValue and flag.nativeValue) != 0
	}
}