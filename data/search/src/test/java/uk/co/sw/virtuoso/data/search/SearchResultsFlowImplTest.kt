package uk.co.sw.virtuoso.data.search

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import uk.co.sw.virtuoso.data.search.models.SearchResultsEntity
import uk.co.sw.virtuoso.data.search.models.SearchResultsModelMapper
import uk.co.sw.virtuoso.domain.search.SearchResultsFlow
import uk.co.sw.virtuoso.domain.search.model.SearchResultModel
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class, ExperimentalCoroutinesApi::class)
class SearchResultsFlowImplTest {

    private val mockSearchResultsModelMapper: SearchResultsModelMapper = mock()

    private lateinit var flowImpl: SearchResultsFlowImpl

    @Before
    fun setUp() {
        flowImpl = SearchResultsFlowImpl(mockSearchResultsModelMapper)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mockSearchResultsModelMapper)
    }

    @Test
    fun `Given impl, when created, then implements SearchResultsFlow interface`() {
        // Given
        // When
        // Then
        assertThat(flowImpl).isInstanceOf(SearchResultsFlow::class.java)
    }

    @Test
    fun `Given impl, when created, then implements EmitSearchResults interface`() {
        // Given
        // When
        // Then
        assertThat(flowImpl).isInstanceOf(EmitSearchResults::class.java)
    }

    @Test
    fun `Given impl, when getting artist flow, then return flow`() {
        // Given
        // When
        val result = flowImpl.getSearchResultsFlow()

        // Then
        assertThat(result).isInstanceOf(Flow::class.java)
    }

    @Test
    fun `Given entity, when emitting artist, then emit model to flow`() = runTest {
        // Given
        val mockEntity: SearchResultsEntity = mock()
        val mockModel: SearchResultModel = mock()
        whenever(mockSearchResultsModelMapper.map(mockEntity)).thenReturn(mockModel)

        flowImpl.getSearchResultsFlow().test {
            // When
            flowImpl.emitSearchResults(mockEntity)

            // Then
            assertThat(expectItem()).isEqualTo(mockModel)
            expectNoEvents()
            verify(mockSearchResultsModelMapper).map(mockEntity)
        }
    }

    @Test
    fun `Given null entity, when emitting artist, then emit null to flow`() = runTest {
        // Given
        val mockEntity = null

        flowImpl.getSearchResultsFlow().test {
            // When
            flowImpl.emitSearchResults(mockEntity)

            // Then
            assertThat(expectItem()).isEqualTo(null)
            expectNoEvents()
        }
    }
}