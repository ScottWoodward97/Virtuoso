package uk.co.sw.virtuoso.domain.search

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

@OptIn(ExperimentalCoroutinesApi::class)
class SearchArtistUseCaseTest {

    private val mockSearchRepository: SearchRepository = mock()
    private lateinit var useCase: SearchArtistUseCase

    @Before
    fun setUp() {
        useCase = SearchArtistUseCase(mockSearchRepository)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mockSearchRepository)
    }

    @Test
    fun `Given initialised, when use case invoked, then search artist`() = runTest {
        // Given
        val artistName = "name"

        // When
        useCase.invoke(artistName)

        // Then
        verify(mockSearchRepository).searchArtist(artistName)
    }

}