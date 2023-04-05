package uk.co.sw.virtuoso.data.search.models

import uk.co.sw.virtuoso.domain.search.model.SearchResultModel
import javax.inject.Inject

class SearchResultsModelMapper @Inject constructor(
    private val artistSearchModelMapper: ArtistSearchModelMapper,
) {

    fun map(entity: SearchResultsEntity): SearchResultModel {
        return SearchResultModel(
            count = entity.count,
            results = entity.artists.map { artistSearchModelMapper.map(it) }
        )
    }

}