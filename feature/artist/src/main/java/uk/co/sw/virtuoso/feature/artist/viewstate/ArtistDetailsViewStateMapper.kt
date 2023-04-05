package uk.co.sw.virtuoso.feature.artist.viewstate

import android.content.res.Resources
import uk.co.sw.virtuoso.domain.artist.models.ArtistModel
import uk.co.sw.virtuoso.feature.artist.R
import javax.inject.Inject

class ArtistDetailsViewStateMapper @Inject constructor(
    private val resources: Resources,
) {

    fun map(model: ArtistModel?): ArtistDetailsViewState {
        return if (model == null) {
            ArtistDetailsViewState.Error
        } else {
            val viewData = mutableListOf<ArtistDetailsViewData>()
            val artistDuration = resources.getString(
                R.string.artist_details_duration_fmt,
                model.started ?: resources.getString(R.string.artist_details_unknown_start),
                model.ended ?: resources.getString(R.string.artist_details_unknown_end)
            )
            viewData.add(
                ArtistDetailsViewData.ArtistInfoViewData(
                    artistName = model.name,
                    artistType = model.artistType.name,
                    artistDuration = artistDuration,
                )
            )
            if (model.releaseGroups.isNotEmpty()) {
                viewData.add(
                    ArtistDetailsViewData.HeadingSeparatorViewData(
                        resources.getString(R.string.artist_details_releases_header)
                    )
                )
            }
            model.releaseGroups.forEach { releaseGroup ->
                viewData.add(
                    ArtistDetailsViewData.ReleaseGroupViewData(
                        releaseTitle = releaseGroup.title,
                        releaseType = releaseGroup.primaryType.name,
                        releaseDate = releaseGroup.releaseDate,
                    )
                )
            }
            ArtistDetailsViewState.ArtistDetails(viewData)
        }
    }

}