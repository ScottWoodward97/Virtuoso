package uk.co.sw.virtuoso.feature.search.results

sealed class SearchResultsViewState {

    object Loading : SearchResultsViewState()

    object Error : SearchResultsViewState()

    object NoResults : SearchResultsViewState()

    data class SearchResults(
        val count: Int,
        val results: List<ArtistResultViewState>
    ) : SearchResultsViewState() {

        data class ArtistResultViewState(
            val id: String,
            val name: String,
            val disambiguation: String?,
        )
    }
}
