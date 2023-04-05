package uk.co.sw.virtuoso.data.artist.models

import uk.co.sw.virtuoso.domain.artist.models.ArtistModel
import javax.inject.Inject

class ArtistModelMapper @Inject constructor(
    private val releaseGroupModelMapper: ReleaseGroupModelMapper,
) {

    fun map(entity: ArtistEntity): ArtistModel {
        return ArtistModel(
            id = entity.id,
            name = entity.name,
            artistType = entity.type?.let { mapType(it) } ?: ArtistModel.ArtistType.OTHER,
            started = entity.lifeSpan.begin,
            ended = entity.lifeSpan.end,
            releaseGroups = entity.releaseGroups.map { releaseGroupModelMapper.map(it) }
        )
    }

    private fun mapType(type: String): ArtistModel.ArtistType {
        return when (type) {
            "Person" -> ArtistModel.ArtistType.PERSON
            "Group" -> ArtistModel.ArtistType.GROUP
            "Orchestra" -> ArtistModel.ArtistType.ORCHESTRA
            "Choir" -> ArtistModel.ArtistType.CHOIR
            "Character" -> ArtistModel.ArtistType.CHARACTER
            else -> ArtistModel.ArtistType.OTHER
        }
    }

}