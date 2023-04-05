package uk.co.sw.virtuoso.data.artist

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import uk.co.sw.virtuoso.data.artist.models.ArtistEntity
import uk.co.sw.virtuoso.data.artist.models.ArtistModelMapper
import uk.co.sw.virtuoso.domain.artist.ArtistFlow
import uk.co.sw.virtuoso.domain.artist.models.ArtistModel
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
class ArtistFlowImplTest {

    private val mockArtistModelMapper: ArtistModelMapper = mock()

    private lateinit var flowImpl: ArtistFlowImpl

    @Before
    fun setUp() {
        flowImpl = ArtistFlowImpl(mockArtistModelMapper)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mockArtistModelMapper)
    }

    @Test
    fun `Given impl, when created, then implements ArtistFlow interface`() {
        // Given
        // When
        // Then
        assertThat(flowImpl).isInstanceOf(ArtistFlow::class.java)
    }

    @Test
    fun `Given impl, when created, then implements EmitArtist interface`() {
        // Given
        // When
        // Then
        assertThat(flowImpl).isInstanceOf(EmitArtist::class.java)
    }

    @Test
    fun `Given impl, when getting artist flow, then return flow`() {
        // Given
        // When
        val result = flowImpl.getArtistFlow()

        // Then
        assertThat(result).isInstanceOf(Flow::class.java)
    }

    @Test
    fun `Given entity, when emitting artist, then emit entity to flow`() = runTest {
        // Given
        val mockEntity: ArtistEntity = mock()
        val mockModel: ArtistModel = mock()
        whenever(mockArtistModelMapper.map(mockEntity)).thenReturn(mockModel)

        flowImpl.getArtistFlow().test {
            // When
            flowImpl.emitArtist(mockEntity)

            // Then
            assertThat(expectItem()).isEqualTo(mockModel)
            expectNoEvents()
            verify(mockArtistModelMapper).map(mockEntity)
        }
    }

    @Test
    fun `Given entity, when emitting null, then emit null to flow`() = runTest {
        // Given
        val mockEntity = null

        flowImpl.getArtistFlow().test {
            // When
            flowImpl.emitArtist(mockEntity)

            // Then
            assertThat(expectItem()).isEqualTo(null)
            expectNoEvents()
        }
    }

}