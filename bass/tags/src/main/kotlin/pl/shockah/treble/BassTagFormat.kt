package pl.shockah.treble

inline class BassTagFormat(
		val format: kotlin.String
) {
	companion object {
		fun build(vararg elements: Element): BassTagFormat {
			return BassTagFormat(elements.joinToString(""))
		}
	}

	interface Element {
		operator fun plus(element: Element): Compound {
			return when {
				this is Compound && element is Compound -> Compound(values + element.values)
				this is Compound && element !is Compound -> Compound(values + element)
				this !is Compound && element is Compound -> Compound(listOf(this) + element.values)
				else -> Compound(listOf(this, element))
			}
		}

		data class Compound(
				val values: List<Element>
		): Element {
			override fun toString(): kotlin.String {
				return values.joinToString("")
			}
		}
	}

	data class String(
			val value: kotlin.String
	): Element {
		override fun toString(): kotlin.String {
			return value.replace("%", "%%").replace("(", "%(").replace(")", "%)").replace(",", "%,")
		}
	}

	abstract class Tag(
			val tag: kotlin.String
	): Element {
		override fun toString(): kotlin.String {
			return "%$tag"
		}

		object Title: Tag("TITL")

		object Artist: Tag("ARTI")

		object Album: Tag("ALBM")

		object Genre: Tag("GNRE")

		object Year: Tag("YEAR")

		object Comment: Tag("CMNT")

		object TrackNumber: Tag("TRCK")

		object Composer: Tag("COMP")

		object Copyright: Tag("COPY")

		object Subtitle: Tag("SUBT")

		object AlbumArtist: Tag("AART")

		object DiscNumber: Tag("DISC")

		object Publisher: Tag("PUBL")
	}

	data class IfNotEmpty(
			val value: Element,
			val result: Element
	): Element {
		override fun toString(): kotlin.String {
			return "%IFV1($value,$result)"
		}
	}

	data class If(
			val value: Element,
			val result: Element,
			val orElse: Element
	): Element {
		override fun toString(): kotlin.String {
			return "%IFV2($value,$result,$orElse)"
		}
	}

	data class Uppercase(
			val value: Element
	): Element {
		override fun toString(): kotlin.String {
			return "%IUPC($value)"
		}
	}

	data class Lowercase(
			val value: Element
	): Element {
		override fun toString(): kotlin.String {
			return "%ILWC($value)"
		}
	}

	data class CapitalizeWords(
			val value: Element
	): Element {
		override fun toString(): kotlin.String {
			return "%ICAP($value)"
		}
	}

	data class Trim(
			val value: Element
	): Element {
		override fun toString(): kotlin.String {
			return "%ITRM($value)"
		}
	}
}