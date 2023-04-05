package uk.co.sw.virtuoso.data.search.models

import uk.co.sw.virtuoso.domain.search.model.ArtistSearchModel
import javax.inject.Inject

class ArtistSearchModelMapper @Inject constructor() {

    fun map(entity: ArtistSearchEntity): ArtistSearchModel {
        return ArtistSearchModel(
            id = entity.id,
            name = entity.name,
            type = entity.type,
            country = entity.country,
            disambiguation = entity.disambiguation
        )
    }

}