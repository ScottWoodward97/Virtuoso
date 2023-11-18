package uk.co.sw.virtuoso.feature.search.results

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import uk.co.sw.virtuoso.domain.search.model.ArtistSearchModel
import uk.co.sw.virtuoso.domain.search.model.SearchResultModel
import uk.co.sw.virtuoso.feature.search.results.viewstate.SearchResultsViewState
import uk.co.sw.virtuoso.feature.search.results.viewstate.SearchResultsViewStateMapper

class SearchResultsViewStateMapperTest {

    private lateinit var mapper: SearchResultsViewStateMapper

    @Before
    fun setUp() {
        mapper = SearchResultsViewStateMapper()
    }

    @Test
    fun `Given null model, when mapped, then return error view state`() {
        // Given
        val model = null

        // When
        val result = mapper.map(model)

        // Then
        assertThat(result).isEqualTo(SearchResultsViewState.Error)
    }

    @Test
    fun `Given model, when mapped, then return results view state`() {
        // Given
        val mockArtistModel: ArtistSearchModel = mock {
            on { id } doReturn "id"
            on { name } doReturn "name"
            on { disambiguation } doReturn "disambiguation"
        }
        val mockSearchResultsModel = SearchResultModel(
            count = 1,
            results = listOf(mockArtistModel)
        )

        // When
        val result = mapper.map(mockSearchResultsModel)

        // Then
        assertThat(result).isInstanceOf(SearchResultsViewState.SearchResults::class.java)
        assertThat((result as SearchResultsViewState.SearchResults).count).isEqualTo(1)
        assertThat(result.results).hasSize(1)
        with(result.results[0]) {
            assertThat(id).isEqualTo("id")
            assertThat(name).isEqualTo("name")
            assertThat(disambiguation).isEqualTo("disambiguation")
        }
    }
}