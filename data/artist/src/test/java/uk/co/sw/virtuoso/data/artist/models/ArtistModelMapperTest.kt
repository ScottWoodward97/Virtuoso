package uk.co.sw.virtuoso.data.artist.models

import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import uk.co.sw.virtuoso.domain.artist.models.ArtistModel

class ArtistModelMapperTest {

    private val mockReleaseGroupMapper: ReleaseGroupModelMapper = mock()
    private lateinit var mapper: ArtistModelMapper

    @Before
    fun setUp() {
        mapper = ArtistModelMapper(mockReleaseGroupMapper)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mockReleaseGroupMapper)
    }

    @Test
    fun `Given entity, when mapped, then return model`() {
        // Given
        val mockLifeSpanEntity: ArtistEntity.LifeSpanEntity = mock {
            on { begin } doReturn "begin"
            on { end } doReturn "end"
        }
        val mockReleaseGroupEntity: ArtistEntity.ReleaseGroupEntity = mock()
        val entity = ArtistEntity(
            id = "id",
            name = "name",
            type = null,
            lifeSpan = mockLifeSpanEntity,
            releaseGroups = listOf(mockReleaseGroupEntity)
        )

        val mappedReleaseGroupModel: ArtistModel.ReleaseGroupModel = mock()
        whenever(mockReleaseGroupMapper.map(mockReleaseGroupEntity)).thenReturn(
            mappedReleaseGroupModel
        )

        // When
        val result = mapper.map(entity)

        // Then
        verify(mockReleaseGroupMapper).map(mockReleaseGroupEntity)
        with(result) {
            assertThat(id).isEqualTo("id")
            assertThat(name).isEqualTo("name")
            assertThat(artistType).isEqualTo(ArtistModel.ArtistType.OTHER)
            assertThat(started).isEqualTo("begin")
            assertThat(ended).isEqualTo("end")
            assertThat(releaseGroups).containsExactly(mappedReleaseGroupModel)
        }
    }

    @Test
    fun `Given entity, when mapped, then map correct types`() {
        // Given
        val mockLifeSpanEntity: ArtistEntity.LifeSpanEntity = mock {
            on { begin } doReturn "begin"
            on { end } doReturn "end"
        }
        val baseEntity = ArtistEntity(
            id = "id",
            name = "name",
            type = null,
            lifeSpan = mockLifeSpanEntity,
            releaseGroups = emptyList()
        )

        val personEntity = baseEntity.copy(type = "Person")
        val groupEntity = baseEntity.copy(type = "Group")
        val orchestraEntity = baseEntity.copy(type = "Orchestra")
        val choirEntity = baseEntity.copy(type = "Choir")
        val characterEntity = baseEntity.copy(type = "Character")
        val unknownEntity = baseEntity.copy(type = "KJBDKYUWvibdk")
        val nullEntity = baseEntity.copy(type = null)

        // When
        val personModel = mapper.map(personEntity)
        val groupModel = mapper.map(groupEntity)
        val orchestraModel = mapper.map(orchestraEntity)
        val choirModel = mapper.map(choirEntity)
        val characterModel = mapper.map(characterEntity)
        val unknownModel = mapper.map(unknownEntity)
        val nullModel = mapper.map(nullEntity)

        // Then
        assertThat(personModel.artistType).isEqualTo(ArtistModel.ArtistType.PERSON)
        assertThat(groupModel.artistType).isEqualTo(ArtistModel.ArtistType.GROUP)
        assertThat(orchestraModel.artistType).isEqualTo(ArtistModel.ArtistType.ORCHESTRA)
        assertThat(choirModel.artistType).isEqualTo(ArtistModel.ArtistType.CHOIR)
        assertThat(characterModel.artistType).isEqualTo(ArtistModel.ArtistType.CHARACTER)
        assertThat(unknownModel.artistType).isEqualTo(ArtistModel.ArtistType.OTHER)
        assertThat(nullModel.artistType).isEqualTo(ArtistModel.ArtistType.OTHER)

    }
}