package pl.shockah.treble

import pl.shockah.treble.BassNativeConstants as C

open class BassException: Exception() {
	companion object {
		fun buildFromErrorCode(code: Int): BassException {
			return when (code) {
				C.BASS_ERROR_MEM -> Memory()
				C.BASS_ERROR_FILEOPEN -> FileOpen()
				C.BASS_ERROR_DRIVER -> Driver()
				C.BASS_ERROR_BUFLOST -> BufferLost()
				C.BASS_ERROR_HANDLE -> Handle()
				C.BASS_ERROR_FORMAT -> Format()
				C.BASS_ERROR_POSITION -> Position()
				C.BASS_ERROR_INIT -> Init()
				C.BASS_ERROR_START -> Start()
				C.BASS_ERROR_SSL -> SSL()
				C.BASS_ERROR_ALREADY -> Already()
				C.BASS_ERROR_NOCHAN -> NoChannel()
				C.BASS_ERROR_ILLTYPE -> IllegalType()
				C.BASS_ERROR_ILLPARAM -> IllegalParameter()
				C.BASS_ERROR_NO3D -> No3D()
				C.BASS_ERROR_NOEAX -> NoEAX()
				C.BASS_ERROR_DEVICE -> Device()
				C.BASS_ERROR_NOPLAY -> NotPlaying()
				C.BASS_ERROR_FREQ -> Frequency()
				C.BASS_ERROR_NOTFILE -> NotAFile()
				C.BASS_ERROR_NOHW -> NoHardware()
				C.BASS_ERROR_EMPTY -> Empty()
				C.BASS_ERROR_NONET -> NoNetwork()
				C.BASS_ERROR_CREATE -> Create()
				C.BASS_ERROR_NOFX -> NoFX()
				C.BASS_ERROR_NOTAVAIL -> NotAvailable()
				C.BASS_ERROR_DECODE -> Decode()
				C.BASS_ERROR_DX -> AudioSystemNotInstalled()
				C.BASS_ERROR_TIMEOUT -> Timeout()
				C.BASS_ERROR_FILEFORM -> FileFormat()
				C.BASS_ERROR_SPEAKER -> Speaker()
				C.BASS_ERROR_VERSION -> Version()
				C.BASS_ERROR_CODEC -> Codec()
				C.BASS_ERROR_ENDED -> Ended()
				C.BASS_ERROR_BUSY -> Busy()
				C.BASS_ERROR_UNKNOWN -> Unknown()
				else -> throw IllegalArgumentException()
			}
		}
	}

	class Memory: BassException()

	class FileOpen: BassException()

	class Driver: BassException()

	class BufferLost: BassException()

	class Handle: BassException()

	class Format: BassException()

	class Position: BassException()

	class Init: BassException()

	class Start: BassException()

	class SSL: BassException()

	class Already: BassException()

	class NoChannel: BassException()

	class IllegalType: BassException()

	class IllegalParameter: BassException()

	class No3D: BassException()

	class NoEAX: BassException()

	class Device: BassException()

	class NotPlaying: BassException()

	class Frequency: BassException()

	class NotAFile: BassException()

	class NoHardware: BassException()

	class Empty: BassException()

	class NoNetwork: BassException()

	class Create: BassException()

	class NoFX: BassException()

	class NotAvailable: BassException()

	class Decode: BassException()

	// DirectX on Windows, ALSA on Linux
	class AudioSystemNotInstalled: BassException()

	class Timeout: BassException()

	class FileFormat: BassException()

	class Speaker: BassException()

	class Version: BassException()

	class Codec: BassException()

	class Ended: BassException()

	class Busy: BassException()

	class Unknown: BassException()
}