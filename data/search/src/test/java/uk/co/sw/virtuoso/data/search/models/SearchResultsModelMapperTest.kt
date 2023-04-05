package uk.co.sw.virtuoso.data.search.models

import org.assertj.core.api.Assertions.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import uk.co.sw.virtuoso.domain.search.model.ArtistSearchModel

class SearchResultsModelMapperTest {

    private val mockArtistSearchModelMapper: ArtistSearchModelMapper = mock()

    private lateinit var mapper: SearchResultsModelMapper

    @Before
    fun setUp() {
        mapper = SearchResultsModelMapper(mockArtistSearchModelMapper)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mockArtistSearchModelMapper)
    }

    @Test
    fun `Given entity, when mapped, then return model`() {
        // Given
        val mockArtistSearchEntity: ArtistSearchEntity = mock()
        val searchResultsEntity = SearchResultsEntity(
            count = 1,
            artists = listOf(mockArtistSearchEntity)
        )

        val mockMappedArtist: ArtistSearchModel = mock()
        whenever(mockArtistSearchModelMapper.map(mockArtistSearchEntity))
            .thenReturn(mockMappedArtist)

        // When
        val result = mapper.map(searchResultsEntity)

        // Then
        with(result) {
            assertThat(count).isEqualTo(1)
            assertThat(results).containsExactly(mockMappedArtist)
        }
        verify(mockArtistSearchModelMapper).map(mockArtistSearchEntity)
    }

}