package pl.shockah.treble

import com.sun.jna.Pointer
import com.sun.jna.StringArray
import kotlin.properties.Delegates
import pl.shockah.treble.BassEncodeLibrary as L
import pl.shockah.treble.BassEncodeNativeConstants as C

class BassEncoder(
		val handle: Int,
		initialChannel: BassChannel
) {
	var freed = false
		private set

	var channel: BassChannel by Delegates.vetoable(initialChannel) { _, old, new ->
		if (freed)
			throw IllegalStateException()

		if (old == new)
			return@vetoable true

		if (!L.lib.BASS_Encode_SetChannel(handle, new.handle))
			L.throwErrorException()

		return@vetoable true
	}

	var server: BassEncoderServer? = null
		private set

	init {
		L.encoders += this
	}

	protected fun finalize() {
		free()
	}

	fun free() {
		if (freed)
			return

		freed = true
		if (!L.lib.BASS_Encode_Stop(handle))
			L.throwErrorException()
		L.encoders -= this
	}

	fun startServer(
			port: Int,
			bufferLength: Int = 16384,
			burst: Int = bufferLength,
			sendShoutcastMetadata: Boolean = false,
			disableHttpHeaders: Boolean = false,
			connectionListener: ServerConnectionListener? = null
	) {
		if (server != null)
			throw IllegalStateException()

		var flags = if (sendShoutcastMetadata) C.BASS_ENCODE_SERVER_META else 0
		if (disableHttpHeaders)
			flags = flags or C.BASS_ENCODE_SERVER_NOHTTP

		val actualProcessor: BassEncodeNativeLibrary.ENCODECLIENTPROC? = connectionListener?.let { object : BassEncodeNativeLibrary.ENCODECLIENTPROC {
			override fun invoke(handle: Int, connect: Boolean, client: String, headers: StringArray, userData: Pointer?): Boolean {
				val actualHeaders = headers.getStringArray(0).toList()
				if (connect)
					return connectionListener.onClientConnected(client, actualHeaders)
				else
					connectionListener.onClientDisconnected(client)
				return false
			}
		} }

		if (L.lib.BASS_Encode_ServerInit(handle, "$port", bufferLength, burst, flags, actualProcessor, null) == 0)
			L.throwErrorException()
		server = BassEncoderServer(this, port)
	}

	interface ServerConnectionListener {
		fun onClientConnected(client: String, headers: List<String>): Boolean {
			return true
		}

		fun onClientDisconnected(client: String) {
		}
	}
}