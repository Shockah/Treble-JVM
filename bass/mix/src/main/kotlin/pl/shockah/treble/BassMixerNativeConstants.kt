package pl.shockah.treble

object BassMixerNativeConstants {
	// additional BASS_SetConfig option
	const val BASS_CONFIG_MIXER_BUFFER = 0x10601
	const val BASS_CONFIG_MIXER_POSEX = 0x10602
	const val BASS_CONFIG_SPLIT_BUFFER = 0x10610

	// BASS_Mixer_StreamCreate flags
	const val BASS_MIXER_END = 0x10000
	const val BASS_MIXER_NONSTOP = 0x20000
	const val BASS_MIXER_RESUME = 0x1000
	const val BASS_MIXER_POSEX = 0x2000

	// source flags
	const val BASS_MIXER_BUFFER = 0x2000
	const val BASS_MIXER_LIMIT = 0x4000
	const val BASS_MIXER_MATRIX = 0x10000
	const val BASS_MIXER_PAUSE = 0x20000
	const val BASS_MIXER_DOWNMIX = 0x400000
	const val BASS_MIXER_NORAMPIN = 0x800000

	// mixer attributes
	const val BASS_ATTRIB_MIXER_LATENCY = 0x15000

	// splitter flags
	const val BASS_SPLIT_SLAVE = 0x1000
	const val BASS_SPLIT_POS = 0x2000

	// splitter attributes
	const val BASS_ATTRIB_SPLIT_ASYNCBUFFER = 0x15010
	const val BASS_ATTRIB_SPLIT_ASYNCPERIOD = 0x15011

	// envelope types
	const val BASS_MIXER_ENV_FREQ = 1
	const val BASS_MIXER_ENV_VOL = 2
	const val BASS_MIXER_ENV_PAN = 3
	const val BASS_MIXER_ENV_LOOP = 0x10000

	// additional sync type
	const val BASS_SYNC_MIXER_ENVELOPE = 0x10200
	const val BASS_SYNC_MIXER_ENVELOPE_NODE = 0x10201

	// additional BASS_Mixer_ChannelSetPosition flag
	const val BASS_POS_MIXER_RESET = 0x10000

	// BASS_CHANNELINFO type
	const val BASS_CTYPE_STREAM_MIXER = 0x10800
	const val BASS_CTYPE_STREAM_SPLIT = 0x10801
}