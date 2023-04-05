package uk.co.sw.virtuoso.domain.search

import kotlinx.coroutines.flow.Flow
import uk.co.sw.virtuoso.domain.search.model.SearchResultModel

interface SearchResultsFlow {

    fun getSearchResultsFlow(): Flow<SearchResultModel?>

}