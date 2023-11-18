package uk.co.sw.virtuoso.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.co.sw.virtuoso.domain.search.GetSearchResultsFlowUseCase
import uk.co.sw.virtuoso.domain.search.SearchArtistUseCase
import uk.co.sw.virtuoso.feature.core.CoroutineDispatchers
import uk.co.sw.virtuoso.feature.search.results.viewstate.SearchResultsViewState
import uk.co.sw.virtuoso.feature.search.results.viewstate.SearchResultsViewStateMapper
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchArtistUseCase: SearchArtistUseCase,
    private val getSearchResultsFlowUseCase: GetSearchResultsFlowUseCase,
    private val searchResultsViewStateMapper: SearchResultsViewStateMapper,
    private val coroutineDispatchers: CoroutineDispatchers,
) : ViewModel() {

    private companion object {
        const val SEARCH_THROTTLE_THRESHOLD_MILLIS = 300L
    }

    private val _searchResultsViewState =
        MutableStateFlow<SearchResultsViewState>(SearchResultsViewState.NoResults)
    val searchResultsViewState: StateFlow<SearchResultsViewState> = _searchResultsViewState

    private var searchJob: Job? = null

    init {
        viewModelScope.launch(coroutineDispatchers.io()) {
            getSearchResultsFlowUseCase().collect {
                _searchResultsViewState.emit(
                    searchResultsViewStateMapper.map(it)
                )
            }
        }
    }

    fun searchArtist(artistName: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(coroutineDispatchers.io()) {
            _searchResultsViewState.emit(SearchResultsViewState.Loading)
            delay(SEARCH_THROTTLE_THRESHOLD_MILLIS)
            searchArtistUseCase(artistName)
        }
    }

}