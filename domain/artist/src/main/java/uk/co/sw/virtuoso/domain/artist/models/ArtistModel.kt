package uk.co.sw.virtuoso.domain.artist.models

class ArtistModel(
    val id: String,
    val name: String,
    val artistType: ArtistType,
    val started: String?,
    val ended: String?,
    val releaseGroups: List<ReleaseGroupModel>
) {
    enum class ArtistType {
        PERSON,
        GROUP,
        ORCHESTRA,
        CHOIR,
        CHARACTER,
        OTHER,
    }

    data class ReleaseGroupModel(
        val id: String,
        val title: String,
        val primaryType: PrimaryType,
        val secondaryTypes: List<SecondaryType>,
        val releaseDate: String,
        val disambiguation: String,
    ) {
        enum class PrimaryType {
            ALBUM, SINGLE, EP, BROADCAST, OTHER
        }

        enum class SecondaryType {
            COMPILATION,
            SOUNDTRACK,
            SPOKENWORD,
            INTERVIEW,
            AUDIOBOOK,
            AUDIO_DRAMA,
            LIVE,
            REMIX,
            DJ_MIX,
            MIXTAPE,
            DEMO
        }
    }


}