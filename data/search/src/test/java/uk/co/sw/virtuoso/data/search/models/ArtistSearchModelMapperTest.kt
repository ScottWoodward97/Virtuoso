package uk.co.sw.virtuoso.data.search.models

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class ArtistSearchModelMapperTest {

    private lateinit var mapper: ArtistSearchModelMapper

    @Before
    fun setUp() {
        mapper = ArtistSearchModelMapper()
    }

    @Test
    fun `Given entity, when mapped, then return correct model`() {
        // Given
        val entity = ArtistSearchEntity(
            id = "id",
            name = "name",
            type = "type",
            country = "country",
            disambiguation = "disambiguation",
            lifeSpan = mock()
        )

        // When
        val result = mapper.map(entity)

        // Then
        with(result) {
            assertThat(id).isEqualTo("id")
            assertThat(name).isEqualTo("name")
            assertThat(type).isEqualTo("type")
            assertThat(country).isEqualTo("country")
            assertThat(disambiguation).isEqualTo("disambiguation")
        }

    }

}