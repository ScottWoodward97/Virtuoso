package uk.co.sw.virtuoso.feature.artist.viewstate

import android.content.res.Resources
import org.assertj.core.api.Assertions.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import uk.co.sw.virtuoso.domain.artist.models.ArtistModel
import uk.co.sw.virtuoso.feature.artist.R

class ArtistDetailsViewStateMapperTest {

    private val mockResources: Resources = mock()
    private lateinit var mapper: ArtistDetailsViewStateMapper

    @Before
    fun setUp() {
        mapper = ArtistDetailsViewStateMapper(mockResources)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mockResources)
    }

    @Test
    fun `Given null model, when mapped, then return error view state`() {
        // Given
        val model = null

        // When
        val result = mapper.map(model)

        // Then
        assertThat(result).isEqualTo(ArtistDetailsViewState.Error)
    }

    @Test
    fun `Given model with no release groups, when mapped, then return view data`() {
        // Given
        val model: ArtistModel = mock {
            on { name } doReturn "name"
            on { artistType } doReturn ArtistModel.ArtistType.GROUP
            on { started } doReturn null
            on { ended } doReturn null
            on { releaseGroups } doReturn emptyList()
        }
        whenever(mockResources.getString(R.string.artist_details_unknown_start))
            .thenReturn("start")
        whenever(mockResources.getString(R.string.artist_details_unknown_end))
            .thenReturn("end")
        whenever(mockResources.getString(R.string.artist_details_duration_fmt, "start", "end"))
            .thenReturn("Unknown - Present")

        // When
        val result = mapper.map(model)

        // Then
        assertThat(result).isInstanceOf(ArtistDetailsViewState.ArtistDetails::class.java)
        assertThat((result as ArtistDetailsViewState.ArtistDetails).viewData).hasSize(1)
        with(result.viewData[0]) {
            assertThat(this).isInstanceOf(ArtistDetailsViewData.ArtistInfoViewData::class.java)
            assertThat((this as ArtistDetailsViewData.ArtistInfoViewData).artistName).isEqualTo("name")
            assertThat(artistType).isEqualTo("GROUP")
            assertThat(artistDuration).isEqualTo("Unknown - Present")
        }
        verify(mockResources).getString(R.string.artist_details_unknown_start)
        verify(mockResources).getString(R.string.artist_details_unknown_end)
        verify(mockResources).getString(R.string.artist_details_duration_fmt, "start", "end")
    }

    @Test
    fun `Given model with release groups, when mapped, then return view data`() {
        // Given
        val releaseGroup: ArtistModel.ReleaseGroupModel = mock {
            on { title } doReturn "title"
            on { primaryType } doReturn ArtistModel.ReleaseGroupModel.PrimaryType.ALBUM
            on { releaseDate } doReturn "date"
        }
        val model: ArtistModel = mock {
            on { name } doReturn "name"
            on { artistType } doReturn ArtistModel.ArtistType.GROUP
            on { started } doReturn "yesterday"
            on { ended } doReturn "tomorrow"
            on { releaseGroups } doReturn listOf(releaseGroup)
        }
        whenever(
            mockResources.getString(
                R.string.artist_details_duration_fmt,
                "yesterday",
                "tomorrow"
            )
        )
            .thenReturn("yesterday - tomorrow")
        whenever(mockResources.getString(R.string.artist_details_releases_header))
            .thenReturn("Releases")

        // When
        val result = mapper.map(model)

        // Then
        assertThat(result).isInstanceOf(ArtistDetailsViewState.ArtistDetails::class.java)
        assertThat((result as ArtistDetailsViewState.ArtistDetails).viewData).hasSize(3)
        with(result.viewData[0]) {
            assertThat(this).isInstanceOf(ArtistDetailsViewData.ArtistInfoViewData::class.java)
            assertThat((this as ArtistDetailsViewData.ArtistInfoViewData).artistName).isEqualTo("name")
            assertThat(artistType).isEqualTo("GROUP")
            assertThat(artistDuration).isEqualTo("yesterday - tomorrow")
        }
        with(result.viewData[1]) {
            assertThat(this).isInstanceOf(ArtistDetailsViewData.HeadingSeparatorViewData::class.java)
            assertThat((this as ArtistDetailsViewData.HeadingSeparatorViewData).title).isEqualTo("Releases")
        }
        with(result.viewData[2]) {
            assertThat(this).isInstanceOf(ArtistDetailsViewData.ReleaseGroupViewData::class.java)
            assertThat((this as ArtistDetailsViewData.ReleaseGroupViewData).releaseTitle).isEqualTo(
                "title"
            )
            assertThat(releaseType).isEqualTo("ALBUM")
            assertThat(releaseDate).isEqualTo("date")
        }
        verify(mockResources).getString(
            R.string.artist_details_duration_fmt,
            "yesterday",
            "tomorrow"
        )
        verify(mockResources).getString(R.string.artist_details_releases_header)
    }

}