package uk.co.sw.virtuoso.data.search

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import uk.co.sw.virtuoso.data.search.models.SearchResultsEntity
import uk.co.sw.virtuoso.data.search.models.SearchResultsModelMapper
import uk.co.sw.virtuoso.domain.search.model.SearchResultModel
import uk.co.sw.virtuoso.domain.search.SearchResultsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchResultsFlowImpl @Inject constructor(
    private val searchResultsModelMapper: SearchResultsModelMapper,
) : SearchResultsFlow, EmitSearchResults {

    private val _searchResultsFlow = MutableSharedFlow<SearchResultsEntity?>()
    override fun getSearchResultsFlow(): Flow<SearchResultModel?> =
        _searchResultsFlow.map { entity -> entity?.let { searchResultsModelMapper.map(it) } }

    override suspend fun emitSearchResults(entity: SearchResultsEntity?) {
        _searchResultsFlow.emit(entity)
    }

}