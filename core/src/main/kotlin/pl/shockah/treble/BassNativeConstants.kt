package pl.shockah.treble

import com.sun.jna.Platform

object BassNativeConstants {
	// Error codes returned by BASS_ErrorGetCode
	const val BASS_OK = 0
	const val BASS_ERROR_MEM = 1
	const val BASS_ERROR_FILEOPEN = 2
	const val BASS_ERROR_DRIVER = 3
	const val BASS_ERROR_BUFLOST = 4
	const val BASS_ERROR_HANDLE = 5
	const val BASS_ERROR_FORMAT = 6
	const val BASS_ERROR_POSITION = 7
	const val BASS_ERROR_INIT = 8
	const val BASS_ERROR_START = 9
	const val BASS_ERROR_SSL = 10
	const val BASS_ERROR_ALREADY = 14
	const val BASS_ERROR_NOCHAN = 18
	const val BASS_ERROR_ILLTYPE = 19
	const val BASS_ERROR_ILLPARAM = 20
	const val BASS_ERROR_NO3D = 21
	const val BASS_ERROR_NOEAX = 22
	const val BASS_ERROR_DEVICE = 23
	const val BASS_ERROR_NOPLAY = 24
	const val BASS_ERROR_FREQ = 25
	const val BASS_ERROR_NOTFILE = 27
	const val BASS_ERROR_NOHW = 29
	const val BASS_ERROR_EMPTY = 31
	const val BASS_ERROR_NONET = 32
	const val BASS_ERROR_CREATE = 33
	const val BASS_ERROR_NOFX = 34
	const val BASS_ERROR_NOTAVAIL = 37
	const val BASS_ERROR_DECODE = 38
	const val BASS_ERROR_DX = 39
	const val BASS_ERROR_TIMEOUT = 40
	const val BASS_ERROR_FILEFORM = 41
	const val BASS_ERROR_SPEAKER = 42
	const val BASS_ERROR_VERSION = 43
	const val BASS_ERROR_CODEC = 44
	const val BASS_ERROR_ENDED = 45
	const val BASS_ERROR_BUSY = 46
	const val BASS_ERROR_UNKNOWN = -1

	// BASS_SetConfig options
	const val BASS_CONFIG_BUFFER = 0
	const val BASS_CONFIG_UPDATEPERIOD = 1
	const val BASS_CONFIG_GVOL_SAMPLE = 4
	const val BASS_CONFIG_GVOL_STREAM = 5
	const val BASS_CONFIG_GVOL_MUSIC = 6
	const val BASS_CONFIG_CURVE_VOL = 7
	const val BASS_CONFIG_CURVE_PAN = 8
	const val BASS_CONFIG_FLOATDSP = 9
	const val BASS_CONFIG_3DALGORITHM = 10
	const val BASS_CONFIG_NET_TIMEOUT = 11
	const val BASS_CONFIG_NET_BUFFER = 12
	const val BASS_CONFIG_PAUSE_NOPLAY = 13
	const val BASS_CONFIG_NET_PREBUF = 15
	const val BASS_CONFIG_NET_PASSIVE = 18
	const val BASS_CONFIG_REC_BUFFER = 19
	const val BASS_CONFIG_NET_PLAYLIST = 21
	const val BASS_CONFIG_MUSIC_VIRTUAL = 22
	const val BASS_CONFIG_VERIFY = 23
	const val BASS_CONFIG_UPDATETHREADS = 24
	const val BASS_CONFIG_DEV_BUFFER = 27
	const val BASS_CONFIG_REC_LOOPBACK = 28
	const val BASS_CONFIG_VISTA_TRUEPOS = 30
	const val BASS_CONFIG_IOS_MIXAUDIO = 34
	const val BASS_CONFIG_DEV_DEFAULT = 36
	const val BASS_CONFIG_NET_READTIMEOUT = 37
	const val BASS_CONFIG_VISTA_SPEAKERS = 38
	const val BASS_CONFIG_IOS_SPEAKER = 39
	const val BASS_CONFIG_MF_DISABLE = 40
	const val BASS_CONFIG_HANDLES = 41
	const val BASS_CONFIG_UNICODE = 42
	const val BASS_CONFIG_SRC = 43
	const val BASS_CONFIG_SRC_SAMPLE = 44
	const val BASS_CONFIG_ASYNCFILE_BUFFER = 45
	const val BASS_CONFIG_OGG_PRESCAN = 47
	const val BASS_CONFIG_MF_VIDEO = 48
	const val BASS_CONFIG_AIRPLAY = 49
	const val BASS_CONFIG_DEV_NONSTOP = 50
	const val BASS_CONFIG_IOS_NOCATEGORY = 51
	const val BASS_CONFIG_VERIFY_NET = 52
	const val BASS_CONFIG_DEV_PERIOD = 53
	const val BASS_CONFIG_FLOAT = 54
	const val BASS_CONFIG_NET_SEEK = 56
	const val BASS_CONFIG_AM_DISABLE = 58
	const val BASS_CONFIG_NET_PLAYLIST_DEPTH = 59
	const val BASS_CONFIG_NET_PREBUF_WAIT = 60
	const val BASS_CONFIG_WASAPI_PERSIST = 65
	const val BASS_CONFIG_REC_WASAPI = 66

	// BASS_SetConfigPtr options
	const val BASS_CONFIG_NET_AGENT = 16
	const val BASS_CONFIG_NET_PROXY = 17
	const val BASS_CONFIG_IOS_NOTIFY = 46
	const val BASS_CONFIG_LIBSSL = 64

	// BASS_Init flags
	const val BASS_DEVICE_8BITS = 1
	const val BASS_DEVICE_MONO = 2
	const val BASS_DEVICE_3D = 4
	const val BASS_DEVICE_16BITS = 8
	const val BASS_DEVICE_LATENCY = 0x100
	const val BASS_DEVICE_CPSPEAKERS = 0x400
	const val BASS_DEVICE_SPEAKERS = 0x800
	const val BASS_DEVICE_NOSPEAKER = 0x1000
	const val BASS_DEVICE_DMIX = 0x2000
	const val BASS_DEVICE_FREQ = 0x4000
	const val BASS_DEVICE_STEREO = 0x8000
	const val BASS_DEVICE_HOG = 0x10000
	const val BASS_DEVICE_AUDIOTRACK = 0x20000
	const val BASS_DEVICE_DSOUND = 0x40000

	// DirectSound interfaces (for use with BASS_GetDSoundObject)
	const val BASS_OBJECT_DS = 1
	const val BASS_OBJECT_DS3DL = 2

	// BASS_DEVICEINFO flags
	const val BASS_DEVICE_ENABLED = 1
	const val BASS_DEVICE_DEFAULT = 2
	const val BASS_DEVICE_INIT = 4
	const val BASS_DEVICE_LOOPBACK = 8

	const val BASS_DEVICE_TYPE_MASK = 0
	const val BASS_DEVICE_TYPE_NETWORK = 0x01000000
	const val BASS_DEVICE_TYPE_SPEAKERS = 0x02000000
	const val BASS_DEVICE_TYPE_LINE = 0x03000000
	const val BASS_DEVICE_TYPE_HEADPHONES = 0x04000000
	const val BASS_DEVICE_TYPE_MICROPHONE = 0x05000000
	const val BASS_DEVICE_TYPE_HEADSET = 0x06000000
	const val BASS_DEVICE_TYPE_HANDSET = 0x07000000
	const val BASS_DEVICE_TYPE_DIGITAL = 0x08000000
	const val BASS_DEVICE_TYPE_SPDIF = 0x09000000
	const val BASS_DEVICE_TYPE_HDMI = 0x0
	const val BASS_DEVICE_TYPE_DISPLAYPORT = 0x40000000

	// BASS_GetDeviceInfo flags
	const val BASS_DEVICES_AIRPLAY = 0x1000000

	// BASS_INFO flags (from DSOUND.H)
	const val DSCAPS_CONTINUOUSRATE = 0x00000010
	const val DSCAPS_EMULDRIVER = 0x00000020
	const val DSCAPS_CERTIFIED = 0x00000040
	const val DSCAPS_SECONDARYMONO = 0x00000100
	const val DSCAPS_SECONDARYSTEREO = 0x00000200
	const val DSCAPS_SECONDARY8BIT = 0x00000400
	const val DSCAPS_SECONDARY16BIT = 0x00000800

	// BASS_RECORDINFO flags (from DSOUND.H)
	const val DSCCAPS_EMULDRIVER = DSCAPS_EMULDRIVER
	const val DSCCAPS_CERTIFIED = DSCAPS_CERTIFIED

	// defines for formats field of BASS_RECORDINFO (from MMSYSTEM.H)
	const val WAVE_FORMAT_1M08 = 0x00000001
	const val WAVE_FORMAT_1S08 = 0x00000002
	const val WAVE_FORMAT_1M16 = 0x00000004
	const val WAVE_FORMAT_1S16 = 0x00000008
	const val WAVE_FORMAT_2M08 = 0x00000010
	const val WAVE_FORMAT_2S08 = 0x00000020
	const val WAVE_FORMAT_2M16 = 0x00000040
	const val WAVE_FORMAT_2S16 = 0x00000080
	const val WAVE_FORMAT_4M08 = 0x00000100
	const val WAVE_FORMAT_4S08 = 0x00000200
	const val WAVE_FORMAT_4M16 = 0x00000400
	const val WAVE_FORMAT_4S16 = 0x00000800

	const val BASS_SAMPLE_8BITS = 1
	const val BASS_SAMPLE_FLOAT = 256
	const val BASS_SAMPLE_MONO = 2
	const val BASS_SAMPLE_LOOP = 4
	const val BASS_SAMPLE_3D = 8
	const val BASS_SAMPLE_SOFTWARE = 16
	const val BASS_SAMPLE_MUTEMAX = 32
	const val BASS_SAMPLE_VAM = 64
	const val BASS_SAMPLE_FX = 128
	const val BASS_SAMPLE_OVER_VOL = 0x10000
	const val BASS_SAMPLE_OVER_POS = 0x20000
	const val BASS_SAMPLE_OVER_DIST = 0x30000

	const val BASS_STREAM_PRESCAN = 0x20000
	const val BASS_STREAM_AUTOFREE = 0x40000
	const val BASS_STREAM_RESTRATE = 0x80000
	const val BASS_STREAM_BLOCK = 0x100000
	const val BASS_STREAM_DECODE = 0x200000
	const val BASS_STREAM_STATUS = 0x800000

	const val BASS_MP3_IGNOREDELAY = 0x200
	const val BASS_MP3_SETPOS = BASS_STREAM_PRESCAN

	const val BASS_MUSIC_FLOAT = BASS_SAMPLE_FLOAT
	const val BASS_MUSIC_MONO = BASS_SAMPLE_MONO
	const val BASS_MUSIC_LOOP = BASS_SAMPLE_LOOP
	const val BASS_MUSIC_3D = BASS_SAMPLE_3D
	const val BASS_MUSIC_FX = BASS_SAMPLE_FX
	const val BASS_MUSIC_AUTOFREE = BASS_STREAM_AUTOFREE
	const val BASS_MUSIC_DECODE = BASS_STREAM_DECODE
	const val BASS_MUSIC_PRESCAN = BASS_STREAM_PRESCAN
	const val BASS_MUSIC_CALCLEN = BASS_MUSIC_PRESCAN
	const val BASS_MUSIC_RAMP = 0x200
	const val BASS_MUSIC_RAMPS = 0x400
	const val BASS_MUSIC_SURROUND = 0x800
	const val BASS_MUSIC_SURROUND2 = 0x1000
	const val BASS_MUSIC_FT2PAN = 0x2000
	const val BASS_MUSIC_FT2MOD = 0x2000
	const val BASS_MUSIC_PT1MOD = 0x4000
	const val BASS_MUSIC_NONINTER = 0x10000
	const val BASS_MUSIC_SINCINTER = 0x800000
	const val BASS_MUSIC_POSRESET = 0x8000
	const val BASS_MUSIC_POSRESETEX = 0x400000
	const val BASS_MUSIC_STOPBACK = 0x80000
	const val BASS_MUSIC_NOSAMPLE = 0x100000

	// Speaker assignment flags
	const val BASS_SPEAKER_FRONT = 0x1000000
	const val BASS_SPEAKER_REAR = 0x2000000
	const val BASS_SPEAKER_CENLFE = 0x3000000
	const val BASS_SPEAKER_REAR2 = 0x4000000
	// 	const val BASS_SPEAKER_N(n) = ((n)<<24)
	const val BASS_SPEAKER_LEFT = 0x10000000
	const val BASS_SPEAKER_RIGHT = 0x20000000
	const val BASS_SPEAKER_FRONTLEFT = BASS_SPEAKER_FRONT or BASS_SPEAKER_LEFT
	const val BASS_SPEAKER_FRONTRIGHT = BASS_SPEAKER_FRONT or BASS_SPEAKER_RIGHT
	const val BASS_SPEAKER_REARLEFT = BASS_SPEAKER_REAR or BASS_SPEAKER_LEFT
	const val BASS_SPEAKER_REARRIGHT = BASS_SPEAKER_REAR or BASS_SPEAKER_RIGHT
	const val BASS_SPEAKER_CENTER = BASS_SPEAKER_CENLFE or BASS_SPEAKER_LEFT
	const val BASS_SPEAKER_LFE = BASS_SPEAKER_CENLFE or BASS_SPEAKER_RIGHT
	const val BASS_SPEAKER_REAR2LEFT = BASS_SPEAKER_REAR2 or BASS_SPEAKER_LEFT
	const val BASS_SPEAKER_REAR2RIGHT = BASS_SPEAKER_REAR2 or BASS_SPEAKER_RIGHT

	const val BASS_ASYNCFILE = 0x40000000
	const val BASS_UNICODE = 0x80000000U

	val BASS_UNICODE_PLATFORM: UInt by lazy { if (Platform.isWindows()) BASS_UNICODE else 0.toUInt() }

	const val BASS_RECORD_PAUSE = 0x8000
	const val BASS_RECORD_ECHOCANCEL = 0x2000
	const val BASS_RECORD_AGC = 0x4000

	// DX7 voice allocation & management flags
	const val BASS_VAM_HARDWARE = 1
	const val BASS_VAM_SOFTWARE = 2
	const val BASS_VAM_TERM_TIME = 4
	const val BASS_VAM_TERM_DIST = 8
	const val BASS_VAM_TERM_PRIO = 16

	const val BASS_ORIGRES_FLOAT = 0x10000

	// BASS_CHANNELINFO types
	const val BASS_CTYPE_SAMPLE = 1
	const val BASS_CTYPE_RECORD = 2
	const val BASS_CTYPE_STREAM = 0x10000
	const val BASS_CTYPE_STREAM_OGG = 0x10002
	const val BASS_CTYPE_STREAM_MP1 = 0x10003
	const val BASS_CTYPE_STREAM_MP2 = 0x10004
	const val BASS_CTYPE_STREAM_MP3 = 0x10005
	const val BASS_CTYPE_STREAM_AIFF = 0x10006
	const val BASS_CTYPE_STREAM_CA = 0x10007
	const val BASS_CTYPE_STREAM_MF = 0x10008
	const val BASS_CTYPE_STREAM_AM = 0x10009
	const val BASS_CTYPE_STREAM_DUMMY = 0x18000
	const val BASS_CTYPE_STREAM_DEVICE = 0x18001
	const val BASS_CTYPE_STREAM_WAV = 0x40000
	const val BASS_CTYPE_STREAM_WAV_PCM = 0x50001
	const val BASS_CTYPE_STREAM_WAV_FLOAT = 0x50003
	const val BASS_CTYPE_MUSIC_MOD = 0x20000
	const val BASS_CTYPE_MUSIC_MTM = 0x20001
	const val BASS_CTYPE_MUSIC_S3M = 0x20002
	const val BASS_CTYPE_MUSIC_XM = 0x20003
	const val BASS_CTYPE_MUSIC_IT = 0x20004
	const val BASS_CTYPE_MUSIC_MO3 = 0x00100

	// 3D channel modes
	const val BASS_3DMODE_NORMAL = 0
	const val BASS_3DMODE_RELATIVE = 1
	const val BASS_3DMODE_OFF = 2

	// software 3D mixing algorithms (used with BASS_CONFIG_3DALGORITHM)
	const val BASS_3DALG_DEFAULT = 0
	const val BASS_3DALG_OFF = 1
	const val BASS_3DALG_FULL = 2
	const val BASS_3DALG_LIGHT = 3

	// EAX presets, usage: BASS_SetEAXParameters(EAX_PRESET_xxx)
//	#define EAX_PRESET_GENERIC         EAX_ENVIRONMENT_GENERIC,0.5F,1.493F,0.5F
//	#define EAX_PRESET_PADDEDCELL      EAX_ENVIRONMENT_PADDEDCELL,0.25F,0.1F,0.0F
//	#define EAX_PRESET_ROOM            EAX_ENVIRONMENT_ROOM,0.417F,0.4F,0.666F
//	#define EAX_PRESET_BATHROOM        EAX_ENVIRONMENT_BATHROOM,0.653F,1.499F,0.166F
//	#define EAX_PRESET_LIVINGROOM      EAX_ENVIRONMENT_LIVINGROOM,0.208F,0.478F,0.0F
//	#define EAX_PRESET_STONEROOM       EAX_ENVIRONMENT_STONEROOM,0.5F,2.309F,0.888F
//	#define EAX_PRESET_AUDITORIUM      EAX_ENVIRONMENT_AUDITORIUM,0.403F,4.279F,0.5F
//	#define EAX_PRESET_CONCERTHALL     EAX_ENVIRONMENT_CONCERTHALL,0.5F,3.961F,0.5F
//	#define EAX_PRESET_CAVE            EAX_ENVIRONMENT_CAVE,0.5F,2.886F,1.304F
//	#define EAX_PRESET_ARENA           EAX_ENVIRONMENT_ARENA,0.361F,7.284F,0.332F
//	#define EAX_PRESET_HANGAR          EAX_ENVIRONMENT_HANGAR,0.5F,10.0F,0.3F
//	#define EAX_PRESET_CARPETEDHALLWAY EAX_ENVIRONMENT_CARPETEDHALLWAY,0.153F,0.259F,2.0F
//	#define EAX_PRESET_HALLWAY         EAX_ENVIRONMENT_HALLWAY,0.361F,1.493F,0.0F
//	#define EAX_PRESET_STONECORRIDOR   EAX_ENVIRONMENT_STONECORRIDOR,0.444F,2.697F,0.638F
//	#define EAX_PRESET_ALLEY           EAX_ENVIRONMENT_ALLEY,0.25F,1.752F,0.776F
//	#define EAX_PRESET_FOREST          EAX_ENVIRONMENT_FOREST,0.111F,3.145F,0.472F
//	#define EAX_PRESET_CITY            EAX_ENVIRONMENT_CITY,0.111F,2.767F,0.224F
//	#define EAX_PRESET_MOUNTAINS       EAX_ENVIRONMENT_MOUNTAINS,0.194F,7.841F,0.472F
//	#define EAX_PRESET_QUARRY          EAX_ENVIRONMENT_QUARRY,1.0F,1.499F,0.5F
//	#define EAX_PRESET_PLAIN           EAX_ENVIRONMENT_PLAIN,0.097F,2.767F,0.224F
//	#define EAX_PRESET_PARKINGLOT      EAX_ENVIRONMENT_PARKINGLOT,0.208F,1.652F,1.5F
//	#define EAX_PRESET_SEWERPIPE       EAX_ENVIRONMENT_SEWERPIPE,0.652F,2.886F,0.25F
//	#define EAX_PRESET_UNDERWATER      EAX_ENVIRONMENT_UNDERWATER,1.0F,1.499F,0.0F
//	#define EAX_PRESET_DRUGGED         EAX_ENVIRONMENT_DRUGGED,0.875F,8.392F,1.388F
//	#define EAX_PRESET_DIZZY           EAX_ENVIRONMENT_DIZZY,0.139F,17.234F,0.666F
//	#define EAX_PRESET_PSYCHOTIC       EAX_ENVIRONMENT_PSYCHOTIC,0.486F,7.563F,0.806F

	const val BASS_STREAMPROC_END = 0x80000000U

	// special STREAMPROCs
	const val STREAMPROC_DUMMY = 0
	const val STREAMPROC_PUSH = -1
	const val STREAMPROC_DEVICE = -2
	const val STREAMPROC_DEVICE_3D = -3

	// BASS_StreamCreateFileUser file systems
	const val STREAMFILE_NOBUFFER = 0
	const val STREAMFILE_BUFFER = 1
	const val STREAMFILE_BUFFERPUSH = 2

	// BASS_StreamPutFileData options
	const val BASS_FILEDATA_END = 0

	// BASS_StreamGetFilePosition modes
	const val BASS_FILEPOS_CURRENT = 0
	const val BASS_FILEPOS_DECODE = BASS_FILEPOS_CURRENT
	const val BASS_FILEPOS_DOWNLOAD = 1
	const val BASS_FILEPOS_END = 2
	const val BASS_FILEPOS_START = 3
	const val BASS_FILEPOS_CONNECTED = 4
	const val BASS_FILEPOS_BUFFER = 5
	const val BASS_FILEPOS_SOCKET = 6
	const val BASS_FILEPOS_ASYNCBUF = 7
	const val BASS_FILEPOS_SIZE = 8
	const val BASS_FILEPOS_BUFFERING = 9

	// BASS_ChannelSetSync types
	const val BASS_SYNC_POS = 0
	const val BASS_SYNC_END = 2
	const val BASS_SYNC_META = 4
	const val BASS_SYNC_SLIDE = 5
	const val BASS_SYNC_STALL = 6
	const val BASS_SYNC_DOWNLOAD = 7
	const val BASS_SYNC_FREE = 8
	const val BASS_SYNC_SETPOS = 11
	const val BASS_SYNC_MUSICPOS = 10
	const val BASS_SYNC_MUSICINST = 1
	const val BASS_SYNC_MUSICFX = 3
	const val BASS_SYNC_OGG_CHANGE = 12
	const val BASS_SYNC_DEV_FAIL = 14
	const val BASS_SYNC_DEV_FORMAT = 15
	const val BASS_SYNC_MIXTIME = 0x40000000
	const val BASS_SYNC_ONETIME = 0x80000000U

	// BASS_ChannelIsActive return values
	const val BASS_ACTIVE_STOPPED = 0
	const val BASS_ACTIVE_PLAYING = 1
	const val BASS_ACTIVE_STALLED = 2
	const val BASS_ACTIVE_PAUSED = 3
	const val BASS_ACTIVE_PAUSED_DEVICE = 4

	// Channel attributes
	const val BASS_ATTRIB_FREQ = 1
	const val BASS_ATTRIB_VOL = 2
	const val BASS_ATTRIB_PAN = 3
	const val BASS_ATTRIB_EAXMIX = 4
	const val BASS_ATTRIB_NOBUFFER = 5
	const val BASS_ATTRIB_VBR = 6
	const val BASS_ATTRIB_CPU = 7
	const val BASS_ATTRIB_SRC = 8
	const val BASS_ATTRIB_NET_RESUME = 9
	const val BASS_ATTRIB_SCANINFO = 10
	const val BASS_ATTRIB_NORAMP = 11
	const val BASS_ATTRIB_BITRATE = 12
	const val BASS_ATTRIB_BUFFER = 13
	const val BASS_ATTRIB_MUSIC_AMPLIFY = 0x100
	const val BASS_ATTRIB_MUSIC_PANSEP = 0x101
	const val BASS_ATTRIB_MUSIC_PSCALER = 0x102
	const val BASS_ATTRIB_MUSIC_BPM = 0x103
	const val BASS_ATTRIB_MUSIC_SPEED = 0x104
	const val BASS_ATTRIB_MUSIC_VOL_GLOBAL = 0x105
	const val BASS_ATTRIB_MUSIC_ACTIVE = 0x106
	const val BASS_ATTRIB_MUSIC_VOL_CHAN = 0x200
	const val BASS_ATTRIB_MUSIC_VOL_INST = 0x300

	// BASS_ChannelSlideAttribute flags
	const val BASS_SLIDE_LOG = 0x1000000

	// BASS_ChannelGetData flags
	const val BASS_DATA_AVAILABLE = 0
	const val BASS_DATA_FIXED = 0x20000000
	const val BASS_DATA_FLOAT = 0x40000000
	const val BASS_DATA_FFT256 = 0x80000000U
	const val BASS_DATA_FFT512 = 0x80000001U
	const val BASS_DATA_FFT1024 = 0x80000002U
	const val BASS_DATA_FFT2048 = 0x80000003U
	const val BASS_DATA_FFT4096 = 0x80000004U
	const val BASS_DATA_FFT8192 = 0x80000005U
	const val BASS_DATA_FFT16384 = 0x80000006U
	const val BASS_DATA_FFT32768 = 0x80000007U
	const val BASS_DATA_FFT_INDIVIDUAL = 0x10
	const val BASS_DATA_FFT_NOWINDOW = 0x20
	const val BASS_DATA_FFT_REMOVEDC = 0x40
	const val BASS_DATA_FFT_COMPLEX = 0x80
	const val BASS_DATA_FFT_NYQUIST = 0x100

	// BASS_ChannelGetLevelEx flags
	const val BASS_LEVEL_MONO = 1
	const val BASS_LEVEL_STEREO = 2
	const val BASS_LEVEL_RMS = 4
	const val BASS_LEVEL_VOLPAN = 8

	// BASS_ChannelGetTags types : what's returned
	const val BASS_TAG_ID3 = 0
	const val BASS_TAG_ID3V2 = 1
	const val BASS_TAG_OGG = 2
	const val BASS_TAG_HTTP = 3
	const val BASS_TAG_ICY = 4
	const val BASS_TAG_META = 5
	const val BASS_TAG_APE = 6
	const val BASS_TAG_MP4 = 7
	const val BASS_TAG_WMA = 8
	const val BASS_TAG_VENDOR = 9
	const val BASS_TAG_LYRICS3 = 10
	const val BASS_TAG_CA_CODEC = 11
	const val BASS_TAG_MF = 13
	const val BASS_TAG_WAVEFORMAT = 14
	const val BASS_TAG_AM_MIME = 15
	const val BASS_TAG_AM_NAME = 16
	const val BASS_TAG_RIFF_INFO = 0x100
	const val BASS_TAG_RIFF_BEXT = 0x101
	const val BASS_TAG_RIFF_CART = 0x102
	const val BASS_TAG_RIFF_DISP = 0x103
	const val BASS_TAG_RIFF_CUE = 0x104
	const val BASS_TAG_RIFF_SMPL = 0x105
	const val BASS_TAG_APE_BINARY = 0x1000
	const val BASS_TAG_MUSIC_NAME = 0x10000
	const val BASS_TAG_MUSIC_MESSAGE = 0x10001
	const val BASS_TAG_MUSIC_ORDERS = 0x10002
	const val BASS_TAG_MUSIC_AUTH = 0x10003
	const val BASS_TAG_MUSIC_INST = 0x10100
	const val BASS_TAG_MUSIC_SAMPLE = 0x10300

	// BASS_ChannelGetLength/GetPosition/SetPosition modes
	const val BASS_POS_BYTE = 0
	const val BASS_POS_MUSIC_ORDER = 1
	const val BASS_POS_OGG = 3
	const val BASS_POS_RESET = 0x2000000
	const val BASS_POS_RELATIVE = 0x4000000
	const val BASS_POS_INEXACT = 0x8000000
	const val BASS_POS_DECODE = 0x10000000
	const val BASS_POS_DECODETO = 0x20000000
	const val BASS_POS_SCAN = 0x40000000

	// BASS_ChannelSetDevice/GetDevice option
	const val BASS_NODEVICE = 0x20000

	// BASS_RecordSetInput flags
	const val BASS_INPUT_OFF = 0x10000
	const val BASS_INPUT_ON = 0x20000

	const val BASS_INPUT_TYPE_MASK = 0xff000000
	const val BASS_INPUT_TYPE_UNDEF = 0x00000000
	const val BASS_INPUT_TYPE_DIGITAL = 0x01000000
	const val BASS_INPUT_TYPE_LINE = 0x02000000
	const val BASS_INPUT_TYPE_MIC = 0x03000000
	const val BASS_INPUT_TYPE_SYNTH = 0x04000000
	const val BASS_INPUT_TYPE_CD = 0x05000000
	const val BASS_INPUT_TYPE_PHONE = 0x06000000
	const val BASS_INPUT_TYPE_SPEAKER = 0x07000000
	const val BASS_INPUT_TYPE_WAVE = 0x08000000
	const val BASS_INPUT_TYPE_AUX = 0x09000000
	const val BASS_INPUT_TYPE_ANALOG = 0x0a000000

	// BASS_ChannelSetFX effect types
	const val BASS_FX_DX8_CHORUS = 0
	const val BASS_FX_DX8_COMPRESSOR = 1
	const val BASS_FX_DX8_DISTORTION = 2
	const val BASS_FX_DX8_ECHO = 3
	const val BASS_FX_DX8_FLANGER = 4
	const val BASS_FX_DX8_GARGLE = 5
	const val BASS_FX_DX8_I3DL2REVERB = 6
	const val BASS_FX_DX8_PARAMEQ = 7
	const val BASS_FX_DX8_REVERB = 8
	const val BASS_FX_VOLUME = 9

	const val BASS_DX8_PHASE_NEG_180 = 0
	const val BASS_DX8_PHASE_NEG_90 = 1
	const val BASS_DX8_PHASE_ZERO = 2
	const val BASS_DX8_PHASE_90 = 3
	const val BASS_DX8_PHASE_180 = 4

	const val BASS_IOSNOTIFY_INTERRUPT = 1
	const val BASS_IOSNOTIFY_INTERRUPT_END = 2
}