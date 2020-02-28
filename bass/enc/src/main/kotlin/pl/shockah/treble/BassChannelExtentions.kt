package pl.shockah.treble

import com.sun.jna.Platform
import com.sun.jna.Pointer
import com.sun.jna.WString
import java.io.File
import java.lang.ref.WeakReference
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicReference
import pl.shockah.treble.BassEncodeLibrary as L
import pl.shockah.treble.BassEncodeNativeConstants as C
import pl.shockah.treble.BassNativeConstants as BC

private fun BassChannel.startGenericEncoder(commandLine: String?, pcm: Boolean, processor: ((encoder: BassEncoder, buffer: ByteBuffer) -> Unit)? = null, flags: BassEncoderFlag = BassEncoderFlag.None): BassEncoder {
	if (freed)
		throw IllegalStateException()

	val reference = AtomicReference<WeakReference<BassEncoder>>()
	val actualProcessor = processor?.let { object : BassEncodeNativeLibrary.ENCODEPROC {
		override fun invoke(handle: Int, channel: Int, buffer: Pointer, length: Int, userData: Pointer?) {
			reference.get().get()?.let { encoder ->
				val actualBuffer = buffer.getByteBuffer(0, length.toLong())
				processor(encoder, actualBuffer)
			}
		}
	} }

	val actualFlags = flags.nativeValue or (if (pcm) C.BASS_ENCODE_PCM else 0)
	val result = unicodeEncodeStart(handle, commandLine, actualFlags, actualProcessor, null)
	if (result == 0)
		L.throwErrorException()

	val encoder = BassEncoder(result, this)
	reference.set(WeakReference(encoder))
	L.encoders += encoder
	return encoder
}

private fun unicodeEncodeStart(handle: Int, commandLine: String?, flags: Int, proc: BassEncodeNativeLibrary.ENCODEPROC?, userData: Pointer?): Int {
	return when {
		Platform.isWindows() -> L.lib.BASS_Encode_Start(handle, commandLine?.let { WString(it) }, flags or BC.BASS_UNICODE_PLATFORM.toInt(), proc, userData)
		else -> L.lib.BASS_Encode_Start(handle, commandLine, flags, proc, userData)
	}
}

fun BassChannel.startEncoder(commandLine: String, processor: ((encoder: BassEncoder, buffer: ByteBuffer) -> Unit)? = null, flags: BassEncoderFlag = BassEncoderFlag.None): BassEncoder {
	return startGenericEncoder(commandLine, false, processor, flags)
}

fun BassChannel.startPcmEncoder(file: File? = null, processor: ((encoder: BassEncoder, buffer: ByteBuffer) -> Unit)? = null, flags: BassEncoderFlag = BassEncoderFlag.None): BassEncoder {
	return startGenericEncoder(file?.absolutePath, true, processor, flags)
}