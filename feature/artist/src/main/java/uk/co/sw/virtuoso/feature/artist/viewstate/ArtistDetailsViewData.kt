package uk.co.sw.virtuoso.feature.artist.viewstate

sealed class ArtistDetailsViewData {
    data class ArtistInfoViewData(
        val artistName: String,
        val artistType: String,
        val artistDuration: String,
    ) : ArtistDetailsViewData()

    data class HeadingSeparatorViewData(
        val title: String,
    ) : ArtistDetailsViewData()

    data class ReleaseGroupViewData(
        val releaseTitle: String,
        val releaseType: String,
        val releaseDate: String,
    ) : ArtistDetailsViewData()

    enum class ViewType {
        ARTIST_INFO, HEADING_SEPARATOR, RELEASE_GROUP
    }
}