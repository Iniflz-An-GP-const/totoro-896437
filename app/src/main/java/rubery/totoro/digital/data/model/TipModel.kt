package rubery.totoro.digital.data.model

data class TipText(
    val title: String,
    val message: String,
)

/**
 * [texts] maps a two-letter language code ("en", "de", ...) to the tip text.
 * The "en" entry is mandatory: it is the fallback.
 */
data class TipModel(
    val id: Int,
    val texts: Map<String, TipText>,
) {
    val title: String get() = texts.forCurrentLocale().title
    val message: String get() = texts.forCurrentLocale().message
}
