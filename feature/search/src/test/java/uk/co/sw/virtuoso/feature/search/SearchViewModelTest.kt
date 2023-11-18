package uk.co.sw.virtuoso.feature.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*
import uk.co.sw.virtuoso.domain.search.GetSearchResultsFlowUseCase
import uk.co.sw.virtuoso.domain.search.SearchArtistUseCase
import uk.co.sw.virtuoso.domain.search.model.SearchResultModel
import uk.co.sw.virtuoso.feature.search.results.viewstate.SearchResultsViewState
import uk.co.sw.virtuoso.feature.search.results.viewstate.SearchResultsViewStateMapper
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
class SearchViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private val mockSearchArtistUseCase: SearchArtistUseCase = mock()
    private val mockSearchResultsFlow = MutableSharedFlow<SearchResultModel?>()
    private val mockGetSearchResultsFlowUseCase: GetSearchResultsFlowUseCase = mock {
        on { invoke() } doReturn mockSearchResultsFlow
    }
    private val mockSearchResultsViewStateMapper: SearchResultsViewStateMapper = mock()
    private val testDispatchers = TestDispatchers(coroutineRule.dispatcher)

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        viewModel = SearchViewModel(
            searchArtistUseCase = mockSearchArtistUseCase,
            getSearchResultsFlowUseCase = mockGetSearchResultsFlowUseCase,
            searchResultsViewStateMapper = mockSearchResultsViewStateMapper,
            coroutineDispatchers = testDispatchers
        )
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(
            mockSearchArtistUseCase,
            mockGetSearchResultsFlowUseCase,
            mockSearchResultsViewStateMapper
        )
    }

    @Test
    fun `Given artist name, when search artist, then set loading and invoke use case`() = runTest {
        // Given
        val artistName = "name"

        viewModel.searchResultsViewState.test {
            // When
            viewModel.searchArtist(artistName)

            assertThat(expectItem()).isEqualTo(SearchResultsViewState.NoResults)
            assertThat(expectItem()).isEqualTo(SearchResultsViewState.Loading)
            expectNoEvents()
            advanceUntilIdle()

            // Then
            verify(mockGetSearchResultsFlowUseCase).invoke()
            verify(mockSearchArtistUseCase).invoke(artistName)
        }
    }

    @Test
    fun `Given artist name, when search artist twice in limit, then invoke use case once`() =
        runTest {
            // Given
            val artistName = "name"

            viewModel.searchResultsViewState.test {
                // When
                viewModel.searchArtist(artistName)
                advanceTimeBy(300)
                viewModel.searchArtist(artistName)

                assertThat(expectItem()).isEqualTo(SearchResultsViewState.NoResults)
                assertThat(expectItem()).isEqualTo(SearchResultsViewState.Loading)
                expectNoEvents()
                advanceUntilIdle()

                // Then
                verify(mockGetSearchResultsFlowUseCase).invoke()
                verify(mockSearchArtistUseCase).invoke(artistName)
            }
        }

    @Test
    fun `Given artist name, when search artist twice after limit, then invoke use case twice`() =
        runTest {
            // Given
            val artistName = "name"

            viewModel.searchResultsViewState.test {
                // When
                viewModel.searchArtist(artistName)
                advanceTimeBy(301)
                viewModel.searchArtist(artistName)

                assertThat(expectItem()).isEqualTo(SearchResultsViewState.NoResults)
                assertThat(expectItem()).isEqualTo(SearchResultsViewState.Loading)
                expectNoEvents()
                advanceUntilIdle()

                // Then
                verify(mockGetSearchResultsFlowUseCase).invoke()
                verify(mockSearchArtistUseCase, times(2)).invoke(artistName)
            }
        }

    @Test
    fun `Given model, when flow emits, then map and emit view state`() = runTest {
        // Given
        val mockModel: SearchResultModel = mock()
        val mockViewState: SearchResultsViewState.SearchResults = mock()
        whenever(mockSearchResultsViewStateMapper.map(mockModel))
            .thenReturn(mockViewState)

        viewModel.searchResultsViewState.test {
            assertThat(expectItem()).isEqualTo(SearchResultsViewState.NoResults)
            advanceUntilIdle()

            // When
            mockSearchResultsFlow.emit(mockModel)

            // Then
            assertThat(expectItem()).isEqualTo(mockViewState)
            advanceUntilIdle()
            expectNoEvents()

            verify(mockGetSearchResultsFlowUseCase).invoke()
            verify(mockSearchResultsViewStateMapper).map(mockModel)
        }
    }

}