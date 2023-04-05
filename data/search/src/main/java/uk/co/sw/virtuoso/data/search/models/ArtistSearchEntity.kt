package uk.co.sw.virtuoso.data.search.models

data class ArtistSearchEntity(
    val id: String,
    val type: String?,
    val name: String,
    val country: String?,
    val disambiguation: String?,
    val lifeSpan: LifeSpan,
) {
    data class LifeSpan(
        val begin: String,
        val end: String?,
        val ended: Boolean?
    )
}