package uk.co.sw.virtuoso.feature.artist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*
import uk.co.sw.virtuoso.domain.artist.FindArtistUseCase
import uk.co.sw.virtuoso.domain.artist.GetArtistFlowUseCase
import uk.co.sw.virtuoso.domain.artist.models.ArtistModel
import uk.co.sw.virtuoso.feature.artist.viewstate.ArtistDetailsViewState
import uk.co.sw.virtuoso.feature.artist.viewstate.ArtistDetailsViewStateMapper
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
class ArtistDetailsViewModelTest {

    @get:Rule
    internal val rule = InstantTaskExecutorRule()

    @get:Rule
    internal val coroutineRule = TestCoroutineRule()

    private val mockFindArtistUseCase: FindArtistUseCase = mock()
    private val mockArtistFlow = MutableSharedFlow<ArtistModel>()
    private val mockGetArtistFlowUseCase: GetArtistFlowUseCase = mock {
        on { invoke() } doReturn mockArtistFlow
    }
    private val mockArtistDetailsViewStateMapper: ArtistDetailsViewStateMapper = mock()

    private val testDispatcher = TestDispatchers(coroutineRule.dispatcher)

    private lateinit var viewModel: ArtistDetailsViewModel

    @Before
    fun setUp() {
        viewModel = ArtistDetailsViewModel(
            findArtistUseCase = mockFindArtistUseCase,
            getArtistFlowUseCase = mockGetArtistFlowUseCase,
            artistDetailsViewStateMapper = mockArtistDetailsViewStateMapper,
            coroutineDispatchers = testDispatcher
        )
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(
            mockFindArtistUseCase,
            mockGetArtistFlowUseCase,
            mockArtistDetailsViewStateMapper
        )
    }

    @Test
    fun `Given id, when find artist, then show loading and find artist`() = runTest {
        // Given
        val artistId = "id"

        viewModel.artistDetailsViewState.test {
            assertThat(expectItem()).isEqualTo(ArtistDetailsViewState.Loading)
            advanceUntilIdle()

            // When
            viewModel.findArtist(artistId)
            expectNoEvents()

            // Then
            advanceUntilIdle()
            verify(mockGetArtistFlowUseCase).invoke()
            verify(mockFindArtistUseCase).invoke(artistId)
        }
    }

    @Test
    fun `Given model, when artist flow emits, then map and emit view data`() = runTest {
        // Given
        val mockModel: ArtistModel = mock()
        val mockViewState: ArtistDetailsViewState = mock()
        whenever(mockArtistDetailsViewStateMapper.map(mockModel))
            .thenReturn(mockViewState)

        viewModel.artistDetailsViewState.test {
            assertThat(expectItem()).isEqualTo(ArtistDetailsViewState.Loading)
            advanceUntilIdle()
            // When
            mockArtistFlow.emit(mockModel)

            // Then
            assertThat(expectItem()).isEqualTo(mockViewState)
            advanceUntilIdle()
            expectNoEvents()

            verify(mockGetArtistFlowUseCase).invoke()
            verify(mockArtistDetailsViewStateMapper).map(mockModel)
        }
    }
}