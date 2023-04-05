package uk.co.sw.virtuoso.domain.search.model

data class SearchResultModel(
    val count: Int,
    val results: List<ArtistSearchModel>,
)