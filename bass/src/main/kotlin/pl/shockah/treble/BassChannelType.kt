package pl.shockah.treble

import pl.shockah.treble.BassNativeConstants as C

enum class BassChannelType(
		override val nativeValue: Int
): BassFlag {
	Sample(C.BASS_CTYPE_SAMPLE),
	Stream(C.BASS_CTYPE_STREAM),
	DummyStream(C.BASS_CTYPE_STREAM_DUMMY),
	DeviceStream(C.BASS_CTYPE_STREAM_DEVICE),
	OggStream(C.BASS_CTYPE_STREAM_OGG),
	Mp1Stream(C.BASS_CTYPE_STREAM_MP1),
	Mp2Stream(C.BASS_CTYPE_STREAM_MP2),
	Mp3Stream(C.BASS_CTYPE_STREAM_MP3),
	AiffStream(C.BASS_CTYPE_STREAM_AIFF),
	AndroidMediaStream(C.BASS_CTYPE_STREAM_AM),
	CoreAudioStream(C.BASS_CTYPE_STREAM_CA),
	MediaFoundationStream(C.BASS_CTYPE_STREAM_MF),
	WavPcmStream(C.BASS_CTYPE_STREAM_WAV_PCM),
	WavFloatStream(C.BASS_CTYPE_STREAM_WAV_FLOAT),
	WavStream(C.BASS_CTYPE_STREAM_WAV),
	ModMusic(C.BASS_CTYPE_MUSIC_MOD),
	MultiTrackerMusic(C.BASS_CTYPE_MUSIC_MTM),
	ScreamTracker3Music(C.BASS_CTYPE_MUSIC_S3M),
	FastTracker2Music(C.BASS_CTYPE_MUSIC_XM),
	ImpulseTrackerMusic(C.BASS_CTYPE_MUSIC_IT),
	Mo3Music(C.BASS_CTYPE_MUSIC_MO3),
	Recording(C.BASS_CTYPE_RECORD);

	val isWavStream: Boolean
		get() = nativeValue and WavStream.nativeValue != 0

	val isModMusic: Boolean
		get() = nativeValue and ModMusic.nativeValue != 0
}