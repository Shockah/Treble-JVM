package pl.shockah.treble

import com.sun.jna.Platform
import com.sun.jna.WString
import pl.shockah.unikorn.Time
import pl.shockah.unikorn.of
import java.util.concurrent.TimeUnit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import pl.shockah.treble.BassNativeConstants as C

object BassLibrary {
	internal val lib: BassNativeLibrary
		get() = BassNativeLibrary.instance

	internal val channels = mutableSetOf<BassChannel>()

	internal val synchronizers = mutableSetOf<BassSynchronizer<out BassSyncEvent>>()

	val version: String
		get() {
			val result = lib.BASS_GetVersion()
			return listOf(result ushr 24 and 0xFF, result ushr 16 and 0xFF, result ushr 8 and 0xFF, result and 0xFF).joinToString(".") { "$it" }
		}

	var bufferMillisecondLength: Int
		get() = lib.BASS_GetConfig(C.BASS_CONFIG_BUFFER)
		set(value) {
			val actualValue = value.coerceIn(10, 5000)
			if (!lib.BASS_SetConfig(C.BASS_CONFIG_BUFFER, actualValue))
				throwErrorException()
		}

	var updatePeriod: Int?
		get() = lib.BASS_GetConfig(C.BASS_CONFIG_UPDATEPERIOD).takeIf { it != 0 }
		set(value) {
			val actualValue = (value ?: 0).coerceIn(5, 100)
			if (!lib.BASS_SetConfig(C.BASS_CONFIG_UPDATEPERIOD, actualValue))
				throwErrorException()
		}

	var globalSampleVolume: Float
		get() = lib.BASS_GetConfig(C.BASS_CONFIG_GVOL_SAMPLE) / 10000f
		set(value) {
			val actualValue = (value.coerceIn(0f, 1f) * 10000).toInt()
			if (!lib.BASS_SetConfig(C.BASS_CONFIG_GVOL_SAMPLE, actualValue))
				throwErrorException()
		}

	var globalStreamVolume: Float
		get() = lib.BASS_GetConfig(C.BASS_CONFIG_GVOL_STREAM) / 10000f
		set(value) {
			val actualValue = (value.coerceIn(0f, 1f) * 10000).toInt()
			if (!lib.BASS_SetConfig(C.BASS_CONFIG_GVOL_STREAM, actualValue))
				throwErrorException()
		}

	var globalMusicVolume: Float
		get() = lib.BASS_GetConfig(C.BASS_CONFIG_GVOL_MUSIC) / 10000f
		set(value) {
			val actualValue = (value.coerceIn(0f, 1f) * 10000).toInt()
			if (!lib.BASS_SetConfig(C.BASS_CONFIG_GVOL_MUSIC, actualValue))
				throwErrorException()
		}

	var volumeCurve: BassCurve by enumOptionDelegate(C.BASS_CONFIG_CURVE_VOL)

	var panningCurve: BassCurve by enumOptionDelegate(C.BASS_CONFIG_CURVE_PAN)

	var passFloatsToDSP by boolOptionDelegate(C.BASS_CONFIG_FLOATDSP)

	var algorithm3D: BassAlgorithm3D by enumOptionDelegate(C.BASS_CONFIG_3DALGORITHM)

	var updateThreadCount: Int by intOptionDelegate(C.BASS_CONFIG_UPDATETHREADS)

	var updateThread: BassUpdateThread? = null
		private set

	// TODO: option properties for these:
//	const val BASS_CONFIG_NET_TIMEOUT = 11
//	const val BASS_CONFIG_NET_BUFFER = 12
//	const val BASS_CONFIG_PAUSE_NOPLAY = 13
//	const val BASS_CONFIG_NET_PREBUF = 15
//	const val BASS_CONFIG_NET_PASSIVE = 18
//	const val BASS_CONFIG_REC_BUFFER = 19
//	const val BASS_CONFIG_NET_PLAYLIST = 21
//	const val BASS_CONFIG_MUSIC_VIRTUAL = 22
//	const val BASS_CONFIG_VERIFY = 23
//	const val BASS_CONFIG_DEV_BUFFER = 27
//	const val BASS_CONFIG_REC_LOOPBACK = 28
//	const val BASS_CONFIG_VISTA_TRUEPOS = 30
//	const val BASS_CONFIG_IOS_MIXAUDIO = 34
//	const val BASS_CONFIG_NET_READTIMEOUT = 37
//	const val BASS_CONFIG_VISTA_SPEAKERS = 38
//	const val BASS_CONFIG_IOS_SPEAKER = 39
//	const val BASS_CONFIG_MF_DISABLE = 40
//	const val BASS_CONFIG_HANDLES = 41
//	const val BASS_CONFIG_SRC = 43
//	const val BASS_CONFIG_SRC_SAMPLE = 44
//	const val BASS_CONFIG_ASYNCFILE_BUFFER = 45
//	const val BASS_CONFIG_OGG_PRESCAN = 47
//	const val BASS_CONFIG_MF_VIDEO = 48
//	const val BASS_CONFIG_AIRPLAY = 49
//	const val BASS_CONFIG_DEV_NONSTOP = 50
//	const val BASS_CONFIG_IOS_NOCATEGORY = 51
//	const val BASS_CONFIG_VERIFY_NET = 52
//	const val BASS_CONFIG_DEV_PERIOD = 53
//	const val BASS_CONFIG_FLOAT = 54
//	const val BASS_CONFIG_NET_SEEK = 56
//	const val BASS_CONFIG_AM_DISABLE = 58
//	const val BASS_CONFIG_NET_PLAYLIST_DEPTH = 59
//	const val BASS_CONFIG_NET_PREBUF_WAIT = 60
//	const val BASS_CONFIG_WASAPI_PERSIST = 65
//	const val BASS_CONFIG_REC_WASAPI = 66

	internal val internalPlugins = mutableSetOf<BassPlugin>()

	val plugins: Set<BassPlugin>
		get() = internalPlugins

	private val deviceCache = mutableMapOf<Int, BassDevice>()

	val devices: List<BassDevice>
		get() {
			val oldKeys = deviceCache.keys.toList()
			val currentDevices = generateSequence(1) { it + 1 }.map { getLibraryDevice(it) }.takeWhile { it != null }.mapNotNull { it }.toList()
			oldKeys.forEach { key ->
				if (!deviceCache.containsKey(key))
					deviceCache.remove(key)
			}
			return currentDevices
		}

	init {
		if (Platform.isWindows()) {
//			if (!lib.BASS_SetConfig(C.BASS_CONFIG_UNICODE, 1))
//				throwErrorException()
			if (!lib.BASS_SetConfig(C.BASS_CONFIG_DEV_DEFAULT, 1))
				throwErrorException()
		}
	}

	fun stopUpdateThread() {
		updateThread?.interrupt()
		updateThread = null
		updateThreadCount = 1
	}

	fun startUpdateThread(updatePeriod: Time = TimeUnit.MILLISECONDS.of(100), updateLength: Time = updatePeriod * 2) {
		updateThread?.interrupt()
		updateThreadCount = 0
		updateThread = BassUpdateThread(updatePeriod, updateLength).apply { start() }
	}

	fun loadPlugin(name: String): BassPlugin {
		internalPlugins.firstOrNull { it.name == name }?.let { return it }

		val file = JnaHelper.extractLibrary(name)
		val result = unicodePluginLoad(file.absoluteFile.normalize().absolutePath)
		if (result == 0)
			throwErrorException()
		return BassPlugin(result, name).also { internalPlugins += it }
	}

	private fun unicodePluginLoad(file: String): Int {
		return when {
			Platform.isWindows() -> lib.BASS_PluginLoad(WString(file), C.BASS_UNICODE_PLATFORM.toInt())
			else -> lib.BASS_PluginLoad(file, 0)
		}
	}

	internal fun getCachedDevice(id: Int): BassDevice? {
		return deviceCache[id]
	}

	fun update(time: Time) {
		if (!lib.BASS_Update(time.milliseconds.toInt()))
			throwErrorException()
	}

	fun getDevice(id: Int): BassDevice? {
		return getCachedDevice(id) ?: getLibraryDevice(id)
	}

	private fun getLibraryDevice(id: Int): BassDevice? {
		val info = BassNativeLibrary.BASS_DEVICEINFO()
		if (BassNativeLibrary.instance.BASS_GetDeviceInfo(id, info)) {
			val cached = deviceCache[id]
			if (cached?.description == info.stringDescription && cached.driver == info.stringDriver) {
				return cached
			} else {
				val device = BassDevice(id, info.stringDescription, info.stringDriver)
				deviceCache[id] = device
				return device
			}
		} else {
			val errorCode = lib.BASS_ErrorGetCode()
			if (errorCode == C.BASS_ERROR_DEVICE)
				return null
			else
				throwErrorException(errorCode)
		}
	}

	fun throwErrorExceptionIfAny() {
		when (val errorCode = lib.BASS_ErrorGetCode()) {
			0 -> { }
			else -> throwErrorException(errorCode)
		}
	}

	fun throwErrorException(errorCode: Int = lib.BASS_ErrorGetCode()): Nothing {
		throw BassException.buildFromErrorCode(errorCode)
	}

	private fun boolOptionDelegate(option: Int): ReadWriteProperty<Any?, Boolean> {
		return object : ReadWriteProperty<Any?, Boolean> {
			override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
				val raw = lib.BASS_GetConfig(option)
				if (raw == -1)
					throwErrorException()
				return raw != 0
			}

			override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
				if (!lib.BASS_SetConfig(option, if (value) 1 else 0))
					throwErrorException()
			}
		}
	}

	private inline fun <reified E> enumOptionDelegate(option: Int): ReadWriteProperty<Any?, E> where E : Enum<E>, E : BassFlag {
		return object : ReadWriteProperty<Any?, E> {
			override fun getValue(thisRef: Any?, property: KProperty<*>): E {
				val raw = lib.BASS_GetConfig(option)
				if (raw == -1)
					throwErrorException()
				return enumValues<E>().first { it.nativeValue == raw }
			}

			override fun setValue(thisRef: Any?, property: KProperty<*>, value: E) {
				lib.BASS_SetConfig(option, value.nativeValue)
				throwErrorExceptionIfAny()
			}
		}
	}

	private fun intOptionDelegate(option: Int): ReadWriteProperty<Any?, Int> {
		return object : ReadWriteProperty<Any?, Int> {
			override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
				val raw = lib.BASS_GetConfig(option)
				if (raw == -1)
					throwErrorException()
				return raw
			}

			override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
				if (!lib.BASS_SetConfig(option, value))
					throwErrorException()
			}
		}
	}
}