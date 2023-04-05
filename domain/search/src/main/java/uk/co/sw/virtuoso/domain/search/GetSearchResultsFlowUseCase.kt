package uk.co.sw.virtuoso.domain.search

import kotlinx.coroutines.flow.Flow
import uk.co.sw.virtuoso.domain.search.model.SearchResultModel
import javax.inject.Inject

class GetSearchResultsFlowUseCase @Inject constructor(
    private val searchResultsFlow: SearchResultsFlow
) {

    operator fun invoke(): Flow<SearchResultModel?> {
        return searchResultsFlow.getSearchResultsFlow()
    }

}