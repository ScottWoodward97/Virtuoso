package uk.co.sw.virtuoso.domain.search.model

data class ArtistSearchModel(
    val id: String,
    val name: String,
    val type: String?,
    val country: String?,
    val disambiguation: String?,
)