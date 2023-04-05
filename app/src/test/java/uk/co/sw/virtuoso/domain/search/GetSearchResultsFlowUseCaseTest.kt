package uk.co.sw.virtuoso.domain.search

import kotlinx.coroutines.flow.Flow
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import uk.co.sw.virtuoso.domain.search.model.SearchResultModel

class GetSearchResultsFlowUseCaseTest {

    private val mockSearchResultsFlow: SearchResultsFlow = mock()
    private lateinit var useCase: GetSearchResultsFlowUseCase

    @Before
    fun setUp() {
        useCase = GetSearchResultsFlowUseCase(mockSearchResultsFlow)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mockSearchResultsFlow)
    }

    @Test
    fun `Given search results flow, when invoked, then return flow`() {
        // Given
        val mockFlow: Flow<SearchResultModel?> = mock()
        whenever(mockSearchResultsFlow.getSearchResultsFlow())
            .thenReturn(mockFlow)

        // When
        val result = useCase.invoke()

        // Then
        assertThat(result).isEqualTo(mockFlow)
        verify(mockSearchResultsFlow).getSearchResultsFlow()
    }
}