package pl.shockah.treble

object BassEncodeNativeConstants {
	// Additional error codes returned by BASS_ErrorGetCode
	const val BASS_ERROR_ACM_CANCEL = 2000
	const val BASS_ERROR_CAST_DENIED = 2100

	// Additional BASS_SetConfig options
	const val BASS_CONFIG_ENCODE_PRIORITY = 0x10300
	const val BASS_CONFIG_ENCODE_QUEUE = 0x10301
	const val BASS_CONFIG_ENCODE_CAST_TIMEOUT = 0x10310

	// Additional BASS_SetConfigPtr options
	const val BASS_CONFIG_ENCODE_ACM_LOAD = 0x10302
	const val BASS_CONFIG_ENCODE_CAST_PROXY = 0x10311

	// BASS_Encode_Start flags
	const val BASS_ENCODE_NOHEAD = 1
	const val BASS_ENCODE_FP_8BIT = 2
	const val BASS_ENCODE_FP_16BIT = 4
	const val BASS_ENCODE_FP_24BIT = 6
	const val BASS_ENCODE_FP_32BIT = 8
	const val BASS_ENCODE_FP_AUTO = 14
	const val BASS_ENCODE_BIGEND = 16
	const val BASS_ENCODE_PAUSE = 32
	const val BASS_ENCODE_PCM = 64
	const val BASS_ENCODE_RF64 = 128
	const val BASS_ENCODE_MONO = 0x100
	const val BASS_ENCODE_QUEUE = 0x200
	const val BASS_ENCODE_WFEXT = 0x400
	const val BASS_ENCODE_CAST_NOLIMIT = 0x1000
	const val BASS_ENCODE_LIMIT = 0x2000
	const val BASS_ENCODE_AIFF = 0x4000
	const val BASS_ENCODE_DITHER = 0x8000
	const val BASS_ENCODE_AUTOFREE = 0x40000

	// BASS_Encode_GetACMFormat flags
	const val BASS_ACM_DEFAULT = 1
	const val BASS_ACM_RATE = 2
	const val BASS_ACM_CHANS = 4
	const val BASS_ACM_SUGGEST = 8

	// BASS_Encode_GetCount counts
	const val BASS_ENCODE_COUNT_IN = 0
	const val BASS_ENCODE_COUNT_OUT = 1
	const val BASS_ENCODE_COUNT_CAST = 2
	const val BASS_ENCODE_COUNT_QUEUE = 3
	const val BASS_ENCODE_COUNT_QUEUE_LIMIT = 4
	const val BASS_ENCODE_COUNT_QUEUE_FAIL = 5

	// BASS_Encode_CastInit content MIME types
	const val BASS_ENCODE_TYPE_MP3 = "audio/mpeg"
	const val BASS_ENCODE_TYPE_OGG = "audio/ogg"
	const val BASS_ENCODE_TYPE_AAC = "audio/aacp"

	// BASS_Encode_CastGetStats types
	const val BASS_ENCODE_STATS_SHOUT = 0
	const val BASS_ENCODE_STATS_ICE = 1
	const val BASS_ENCODE_STATS_ICESERV = 2

	// Encoder notifications
	const val BASS_ENCODE_NOTIFY_ENCODER = 1
	const val BASS_ENCODE_NOTIFY_CAST = 2
	const val BASS_ENCODE_NOTIFY_CAST_TIMEOUT = 0x10000
	const val BASS_ENCODE_NOTIFY_QUEUE_FULL = 0x10001
	const val BASS_ENCODE_NOTIFY_FREE = 0x10002

	// BASS_Encode_ServerInit flags
	const val BASS_ENCODE_SERVER_NOHTTP = 1
	const val BASS_ENCODE_SERVER_META = 2
}