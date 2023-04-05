package uk.co.sw.virtuoso.data.artist.models

import uk.co.sw.virtuoso.domain.artist.models.ArtistModel
import javax.inject.Inject

class ReleaseGroupModelMapper @Inject constructor() {

    fun map(entity: ArtistEntity.ReleaseGroupEntity): ArtistModel.ReleaseGroupModel {
        return ArtistModel.ReleaseGroupModel(
            id = entity.id,
            title = entity.title,
            primaryType = mapPrimaryType(entity.primaryType),
            secondaryTypes = entity.secondaryTypes.mapNotNull { mapSecondaryType(it) },
            releaseDate = entity.firstReleaseDate,
            disambiguation = entity.disambiguation,
        )
    }

    private fun mapPrimaryType(type: String): ArtistModel.ReleaseGroupModel.PrimaryType {
        return when (type) {
            "Album" -> ArtistModel.ReleaseGroupModel.PrimaryType.ALBUM
            "Single" -> ArtistModel.ReleaseGroupModel.PrimaryType.SINGLE
            "EP" -> ArtistModel.ReleaseGroupModel.PrimaryType.EP
            "Broadcast" -> ArtistModel.ReleaseGroupModel.PrimaryType.BROADCAST
            else -> ArtistModel.ReleaseGroupModel.PrimaryType.OTHER
        }
    }

    private fun mapSecondaryType(type: String): ArtistModel.ReleaseGroupModel.SecondaryType? {
        return when (type) {
            "Compilation" -> ArtistModel.ReleaseGroupModel.SecondaryType.COMPILATION
            "Soundtrack" -> ArtistModel.ReleaseGroupModel.SecondaryType.SOUNDTRACK
            "Spokenword" -> ArtistModel.ReleaseGroupModel.SecondaryType.SPOKENWORD
            "Interview" -> ArtistModel.ReleaseGroupModel.SecondaryType.INTERVIEW
            "Audiobook" -> ArtistModel.ReleaseGroupModel.SecondaryType.AUDIOBOOK
            "Audio drama" -> ArtistModel.ReleaseGroupModel.SecondaryType.AUDIO_DRAMA
            "Live" -> ArtistModel.ReleaseGroupModel.SecondaryType.LIVE
            "Remix" -> ArtistModel.ReleaseGroupModel.SecondaryType.REMIX
            "DJ-mix" -> ArtistModel.ReleaseGroupModel.SecondaryType.DJ_MIX
            "Mixtape" -> ArtistModel.ReleaseGroupModel.SecondaryType.MIXTAPE
            "Demo" -> ArtistModel.ReleaseGroupModel.SecondaryType.DEMO
            else -> null
        }
    }

}
