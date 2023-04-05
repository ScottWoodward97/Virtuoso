package uk.co.sw.virtuoso.domain.artist

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

@OptIn(ExperimentalCoroutinesApi::class)
class FindArtistUseCaseTest {

    private val mockArtistRepository: ArtistRepository = mock()
    private lateinit var useCase: FindArtistUseCase

    @Before
    fun setUp() {
        useCase = FindArtistUseCase(mockArtistRepository)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mockArtistRepository)
    }

    @Test
    fun `Given id, when invoked, then find artist by id`() = runTest {
        // Given
        val artistId = "id"

        // When
        useCase.invoke(artistId)

        // Then
        verify(mockArtistRepository).findArtist(artistId)
    }
}