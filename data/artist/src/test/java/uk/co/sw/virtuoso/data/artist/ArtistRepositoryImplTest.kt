package uk.co.sw.virtuoso.data.artist

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.exceptions.base.MockitoException
import org.mockito.kotlin.*
import retrofit2.Response
import uk.co.sw.virtuoso.data.artist.models.ArtistEntity
import uk.co.sw.virtuoso.domain.artist.ArtistRepository

@OptIn(ExperimentalCoroutinesApi::class)
class ArtistRepositoryImplTest {

    private val mockArtistService: ArtistService = mock()
    private val mockEmitArtist: EmitArtist = mock()

    private lateinit var repositoryImpl: ArtistRepositoryImpl

    @Before
    fun setUp() {
        repositoryImpl = ArtistRepositoryImpl(mockArtistService, mockEmitArtist)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mockArtistService, mockEmitArtist)
    }

    @Test
    fun `Given impl, when created, then implements ArtistRepository`() {
        // Given
        // When
        // Then
        assertThat(repositoryImpl).isInstanceOf(ArtistRepository::class.java)
    }

    @Test
    fun `Given successful response, when find artist, then emit entity`() = runTest {
        // Given
        val mockEntity: ArtistEntity = mock()
        val mockResponse: Response<ArtistEntity> = mock {
            on { isSuccessful } doReturn true
            on { body() } doReturn mockEntity
        }
        whenever(mockArtistService.getArtist("id")).thenReturn(mockResponse)

        // When
        repositoryImpl.findArtist("id")

        // Then
        verify(mockArtistService).getArtist("id")
        val entityCaptor = argumentCaptor<ArtistEntity>()
        verify(mockEmitArtist).emitArtist(entityCaptor.capture())
        assertThat(entityCaptor.lastValue).isEqualTo(mockEntity)
    }

    @Test
    fun `Given unsuccessful response, when find artist, then emit null`() = runTest {
        // Given
        val mockResponse: Response<ArtistEntity> = mock {
            on { isSuccessful } doReturn false
        }
        whenever(mockArtistService.getArtist("id")).thenReturn(mockResponse)

        // When
        repositoryImpl.findArtist("id")

        // Then
        verify(mockArtistService).getArtist("id")
        verify(mockEmitArtist).emitArtist(null)
    }

    @Test
    fun `Given null response body, when find artist, then emit null`() = runTest {
        // Given
        val mockResponse: Response<ArtistEntity> = mock {
            on { isSuccessful } doReturn true
            on { body() } doReturn null
        }
        whenever(mockArtistService.getArtist("id")).thenReturn(mockResponse)

        // When
        repositoryImpl.findArtist("id")

        // Then
        verify(mockArtistService).getArtist("id")
        verify(mockEmitArtist).emitArtist(null)
    }

    @Test
    fun `Given error response, when find artist, then emit null`() = runTest {
        // Given
        whenever(mockArtistService.getArtist("id")).thenThrow(MockitoException("IOException"))

        // When
        repositoryImpl.findArtist("id")

        // Then
        verify(mockArtistService).getArtist("id")
        verify(mockEmitArtist).emitArtist(null)
    }

}