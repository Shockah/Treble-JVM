package pl.shockah.treble

import pl.shockah.unikorn.Time
import pl.shockah.unikorn.minus
import pl.shockah.unikorn.of
import pl.shockah.unikorn.plus
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class BassUpdateThread(
		private val updatePeriod: Time = TimeUnit.MILLISECONDS.of(100),
		private val updateLength: Time = updatePeriod * 2
): Thread() {
	private var nextUpdateAt = Date()

	private val lock = ReentrantLock()

	init {
		isDaemon = true
	}

	fun updateNow() {
		lock.withLock {
			BassLibrary.update(updateLength)
			nextUpdateAt += updatePeriod
		}
	}

	override fun run() {
		try {
			while (!isInterrupted) {
				val now = Date()
				if (nextUpdateAt > now) {
					(nextUpdateAt - now).sleep()
					continue
				}

				updateNow()
			}
		} catch (_: InterruptedException) {
		}
	}
}