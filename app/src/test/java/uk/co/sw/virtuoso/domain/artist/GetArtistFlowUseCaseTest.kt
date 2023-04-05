package uk.co.sw.virtuoso.domain.artist

import kotlinx.coroutines.flow.Flow
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import uk.co.sw.virtuoso.domain.artist.models.ArtistModel

class GetArtistFlowUseCaseTest {

    private val mockArtistFlow: ArtistFlow = mock()
    private lateinit var useCase: GetArtistFlowUseCase

    @Before
    fun setUp() {
        useCase = GetArtistFlowUseCase(mockArtistFlow)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mockArtistFlow)
    }

    @Test
    fun `Given arist flow, when use case invoked, then return flow`() {
        // Given
        val mockFlow: Flow<ArtistModel?> = mock()
        whenever(mockArtistFlow.getArtistFlow())
            .thenReturn(mockFlow)

        // When
        val result = useCase.invoke()

        // Then
        assertThat(result).isEqualTo(mockFlow)
        verify(mockArtistFlow).getArtistFlow()
    }
}