package uk.co.sw.virtuoso.feature.search.results

import uk.co.sw.virtuoso.domain.search.model.SearchResultModel
import javax.inject.Inject

class SearchResultsViewStateMapper @Inject constructor() {

    fun map(model: SearchResultModel?): SearchResultsViewState {
        return if (model == null) {
            SearchResultsViewState.Error
        } else {
            SearchResultsViewState.SearchResults(
                count = model.count,
                results = model.results.map {
                    SearchResultsViewState.SearchResults.ArtistResultViewState(
                        id = it.id,
                        name = it.name,
                        disambiguation = it.disambiguation,
                    )
                }
            )
        }
    }

}