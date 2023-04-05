package uk.co.sw.virtuoso.data.search

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.exceptions.base.MockitoException
import org.mockito.kotlin.*
import retrofit2.Response
import uk.co.sw.virtuoso.data.search.models.SearchResultsEntity
import uk.co.sw.virtuoso.domain.search.SearchRepository

@OptIn(ExperimentalCoroutinesApi::class)
class SearchRepositoryImplTest {

    private val mockSearchService: SearchService = mock()
    private val mockEmitSearchResults: EmitSearchResults = mock()

    private lateinit var repositoryImpl: SearchRepositoryImpl

    @Before
    fun setUp() {
        repositoryImpl = SearchRepositoryImpl(mockSearchService, mockEmitSearchResults)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mockSearchService, mockEmitSearchResults)
    }

    @Test
    fun `Given impl, when created, then implements SearchRepository`() {
        // Given
        // When
        // Then
        assertThat(repositoryImpl).isInstanceOf(SearchRepository::class.java)
    }

    @Test
    fun `Given successful response, when search artist, then emit entity`() = runTest {
        // Given
        val mockEntity: SearchResultsEntity = mock()
        val mockResponse: Response<SearchResultsEntity> = mock {
            on { isSuccessful } doReturn true
            on { body() } doReturn mockEntity
        }
        whenever(mockSearchService.searchArtists("name:artistName"))
            .thenReturn(mockResponse)

        // When
        repositoryImpl.searchArtist("artistName")

        // Then
        verify(mockSearchService).searchArtists("name:artistName")
        val entityCaptor = argumentCaptor<SearchResultsEntity>()
        verify(mockEmitSearchResults).emitSearchResults(entityCaptor.capture())
        assertThat(entityCaptor.lastValue).isEqualTo(mockEntity)
    }

    @Test
    fun `Given unsuccessful response, when search artist, then emit null`() = runTest {
        // Given
        val mockResponse: Response<SearchResultsEntity> = mock {
            on { isSuccessful } doReturn false
        }
        whenever(mockSearchService.searchArtists("name:artistName")).thenReturn(mockResponse)

        // When
        repositoryImpl.searchArtist("artistName")

        // Then
        verify(mockSearchService).searchArtists("name:artistName")
        verify(mockEmitSearchResults).emitSearchResults(null)
    }

    @Test
    fun `Given null response body, when find artist, then emit null`() = runTest {
        // Given
        val mockResponse: Response<SearchResultsEntity> = mock {
            on { isSuccessful } doReturn true
            on { body() } doReturn null
        }
        whenever(mockSearchService.searchArtists("name:artistName"))
            .thenReturn(mockResponse)

        // When
        repositoryImpl.searchArtist("artistName")

        // Then
        verify(mockSearchService).searchArtists("name:artistName")
        verify(mockEmitSearchResults).emitSearchResults(null)
    }

    @Test
    fun `Given error response, when find artist, then emit null`() = runTest {
        // Given
        whenever(mockSearchService.searchArtists("name:artistName"))
            .thenThrow(MockitoException("IOException"))

        // When
        repositoryImpl.searchArtist("artistName")

        // Then
        verify(mockSearchService).searchArtists("name:artistName")
        verify(mockEmitSearchResults).emitSearchResults(null)
    }
}