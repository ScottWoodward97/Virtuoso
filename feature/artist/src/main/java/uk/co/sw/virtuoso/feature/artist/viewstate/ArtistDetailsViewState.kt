package uk.co.sw.virtuoso.feature.artist.viewstate

sealed class ArtistDetailsViewState {
    object Loading : ArtistDetailsViewState()
    object Error : ArtistDetailsViewState()
    data class ArtistDetails(
        val viewData: List<ArtistDetailsViewData>
    ) : ArtistDetailsViewState()
}
