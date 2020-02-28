package pl.shockah.treble

import java.nio.ByteBuffer
import pl.shockah.treble.BassNativeConstants as C

open class BassSyncEvent {
	open class Type<T: BassSyncEvent>(
			val nativeType: Int,
			val factory: (data: Int) -> T
	) {
		open val param: Long = 0L

		open class NoData<T: BassSyncEvent>(
				nativeType: Int,
				result: T
		): Type<T>(nativeType, { result })
	}

	interface Listener<T> {
		fun onSyncEvent(event: T)
	}

	object UnexpectedDeviceStop: BassSyncEvent() {
		object TYPE: Type.NoData<UnexpectedDeviceStop>(C.BASS_SYNC_DEV_FAIL, UnexpectedDeviceStop)
	}

	object SampleFormatChanged: BassSyncEvent() {
		object TYPE: Type.NoData<SampleFormatChanged>(C.BASS_SYNC_DEV_FORMAT, SampleFormatChanged)
	}

	object DownloadFinished: BassSyncEvent() {
		object TYPE: Type.NoData<DownloadFinished>(C.BASS_SYNC_DOWNLOAD, DownloadFinished)
	}

	data class ReachedEnd(
			val modBackwardJump: Boolean
	): BassSyncEvent() {
		object TYPE: Type<ReachedEnd>(C.BASS_SYNC_DOWNLOAD, { data -> ReachedEnd(data != 0) })
	}

	object Freed: BassSyncEvent() {
		object TYPE: Type.NoData<Freed>(C.BASS_SYNC_FREE, Freed)
	}

	object ShoutcastMedatadaReceived: BassSyncEvent() {
		object TYPE: Type.NoData<ShoutcastMedatadaReceived>(C.BASS_SYNC_META, ShoutcastMedatadaReceived)
	}

	abstract class ModSyncEffect: BassSyncEvent() {
		data class Position(
				val order: Int,
				val row: Int
		): ModSyncEffect() {
			object TYPE: Type<Position>(C.BASS_SYNC_MUSICFX, { data -> Position(data and 0xFFFF, data ushr 16 and 0xFFFF) }) {
				override val param = 0L
			}
		}

		data class EffectIndex(
				val effectIndex: Int
		): ModSyncEffect() {
			object TYPE: Type<EffectIndex>(C.BASS_SYNC_MUSICFX, ::EffectIndex) {
				override val param = 1L
			}
		}
	}

	data class ModInstrumentPlayed(
			val note: Int,
			val volume: Int
	): BassSyncEvent() {
		data class Type(
				val instrument: Int,
				val note: Int?
		): BassSyncEvent.Type<ModInstrumentPlayed>(C.BASS_SYNC_MUSICINST, { data -> ModInstrumentPlayed(data and 0xFFFF, data ushr 16 and 0xFFFF) }) {
			override val param by lazy {
				val buffer = ByteBuffer.allocate(8)
				buffer.putInt(note ?: -1)
				buffer.putInt(instrument)
				buffer.flip()
				@Suppress("UsePropertyAccessSyntax")
				return@lazy buffer.getLong()
			}
		}
	}

	data class ModPositionReached(
			val order: Int,
			val row: Int
	): BassSyncEvent() {
		data class Type(
				val order: Int?,
				val row: Int?
		): BassSyncEvent.Type<ModPositionReached>(C.BASS_SYNC_MUSICPOS, { data -> ModPositionReached(data and 0xFFFF, data ushr 16 and 0xFFFF) }) {
			override val param by lazy {
				val buffer = ByteBuffer.allocate(8)
				buffer.putInt(row ?: -1)
				buffer.putInt(order ?: -1)
				buffer.flip()
				@Suppress("UsePropertyAccessSyntax")
				return@lazy buffer.getLong()
			}
		}
	}

	object OggNewBitstreamBegan: BassSyncEvent() {
		object TYPE: Type.NoData<OggNewBitstreamBegan>(C.BASS_SYNC_OGG_CHANGE, OggNewBitstreamBegan)
	}

	object Position: BassSyncEvent() {
		data class Type(
				val position: Int
		): BassSyncEvent.Type.NoData<Position>(C.BASS_SYNC_POS, Position) {
			override val param = position.toLong()
		}
	}

	data class PositionSet(
			val isPlaybackBufferFlushed: Boolean
	): BassSyncEvent() {
		object TYPE: BassSyncEvent.Type<PositionSet>(C.BASS_SYNC_SETPOS, { data -> PositionSet(data != 0) })
	}

	data class AttributeSlideEnded(
			val attributeType: Int
	): BassSyncEvent() {
		object TYPE: BassSyncEvent.Type<AttributeSlideEnded>(C.BASS_SYNC_SLIDE, ::AttributeSlideEnded)
	}

	data class ChannelPlaybackStall(
			val status: Status
	): BassSyncEvent() {
		enum class Status {
			Stalled, Resumed
		}

		object TYPE: BassSyncEvent.Type<ChannelPlaybackStall>(C.BASS_SYNC_STALL, { data -> ChannelPlaybackStall(if (data == 0) Status.Stalled else Status.Resumed) })
	}
}