package uk.co.sw.virtuoso.data.artist.models

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import uk.co.sw.virtuoso.domain.artist.models.ArtistModel

class ReleaseGroupModelMapperTest {

    private lateinit var mapper: ReleaseGroupModelMapper

    @Before
    fun setUp() {
        mapper = ReleaseGroupModelMapper()
    }

    @Test
    fun `Given entity, when mapped, then return model`() {
        // Given
        val entity = ArtistEntity.ReleaseGroupEntity(
            id = "id",
            title = "title",
            primaryType = "Album",
            secondaryTypes = emptyList(),
            firstReleaseDate = "released",
            disambiguation = "disambiguation"
        )

        // When
        val result = mapper.map(entity)

        // Then
        with(result) {
            assertThat(id).isEqualTo("id")
            assertThat(title).isEqualTo("title")
            assertThat(primaryType).isEqualTo(ArtistModel.ReleaseGroupModel.PrimaryType.ALBUM)
            assertThat(secondaryTypes).isEmpty()
            assertThat(releaseDate).isEqualTo("released")
            assertThat(disambiguation).isEqualTo("disambiguation")
        }
    }

    @Test
    fun `Given entity, when mapped, then map correct primary types`() {
        // Given
        val baseEntity = ArtistEntity.ReleaseGroupEntity(
            id = "id",
            title = "title",
            primaryType = "",
            secondaryTypes = emptyList(),
            firstReleaseDate = "released",
            disambiguation = "disambiguation"
        )
        val albumEntity = baseEntity.copy(primaryType = "Album")
        val singleEntity = baseEntity.copy(primaryType = "Single")
        val epEntity = baseEntity.copy(primaryType = "EP")
        val broadcastEntity = baseEntity.copy(primaryType = "Broadcast")
        val unknownEntity = baseEntity.copy(primaryType = "BYBjbefjb")

        // When
        val albumResult = mapper.map(albumEntity)
        val singleResult = mapper.map(singleEntity)
        val epResult = mapper.map(epEntity)
        val broadcastResult = mapper.map(broadcastEntity)
        val unknownResult = mapper.map(unknownEntity)

        // Then
        assertThat(albumResult.primaryType).isEqualTo(ArtistModel.ReleaseGroupModel.PrimaryType.ALBUM)
        assertThat(singleResult.primaryType).isEqualTo(ArtistModel.ReleaseGroupModel.PrimaryType.SINGLE)
        assertThat(epResult.primaryType).isEqualTo(ArtistModel.ReleaseGroupModel.PrimaryType.EP)
        assertThat(broadcastResult.primaryType).isEqualTo(ArtistModel.ReleaseGroupModel.PrimaryType.BROADCAST)
        assertThat(unknownResult.primaryType).isEqualTo(ArtistModel.ReleaseGroupModel.PrimaryType.OTHER)
    }

    @Test
    fun `Given entity, when mapped, then map only correct secondary types`() {
        // Given
        val secondaryTypes = listOf(
            "Compilation",
            "Soundtrack",
            "Spokenword",
            "Interview",
            "Audiobook",
            "Audio drama",
            "Live",
            "Remix",
            "DJ-mix",
            "Mixtape",
            "Demo",
            "Unknown"
        )
        val entity = ArtistEntity.ReleaseGroupEntity(
            id = "id",
            title = "title",
            primaryType = "",
            secondaryTypes = secondaryTypes,
            firstReleaseDate = "released",
            disambiguation = "disambiguation"
        )

        // When
        val result = mapper.map(entity)

        // Then
        assertThat(result.secondaryTypes).containsExactly(
            ArtistModel.ReleaseGroupModel.SecondaryType.COMPILATION,
            ArtistModel.ReleaseGroupModel.SecondaryType.SOUNDTRACK,
            ArtistModel.ReleaseGroupModel.SecondaryType.SPOKENWORD,
            ArtistModel.ReleaseGroupModel.SecondaryType.INTERVIEW,
            ArtistModel.ReleaseGroupModel.SecondaryType.AUDIOBOOK,
            ArtistModel.ReleaseGroupModel.SecondaryType.AUDIO_DRAMA,
            ArtistModel.ReleaseGroupModel.SecondaryType.LIVE,
            ArtistModel.ReleaseGroupModel.SecondaryType.REMIX,
            ArtistModel.ReleaseGroupModel.SecondaryType.DJ_MIX,
            ArtistModel.ReleaseGroupModel.SecondaryType.MIXTAPE,
            ArtistModel.ReleaseGroupModel.SecondaryType.DEMO,
        )
    }

}