package uk.co.sw.virtuoso.data.search.models

data class SearchResultsEntity(
    val count: Int,
    val artists: List<ArtistSearchEntity>,
)