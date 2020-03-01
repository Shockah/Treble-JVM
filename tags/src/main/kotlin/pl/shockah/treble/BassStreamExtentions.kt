package pl.shockah.treble

import pl.shockah.treble.BassTagsLibrary as L

fun BassStream.getTags(vararg elements: BassTagFormat.Element): String {
	return getTags(BassTagFormat(elements.joinToString("")))
}

fun BassStream.getTags(format: BassTagFormat): String {
	return L.lib.TAGS_Read(handle, format.format)
}