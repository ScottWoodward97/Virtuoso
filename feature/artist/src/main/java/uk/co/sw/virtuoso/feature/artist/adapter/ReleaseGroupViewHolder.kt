package uk.co.sw.virtuoso.feature.artist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import uk.co.sw.virtuoso.feature.artist.databinding.ListitemReleaseGroupBinding
import uk.co.sw.virtuoso.feature.artist.viewstate.ArtistDetailsViewData

class ReleaseGroupViewHolder(
    parent: ViewGroup
) : ArtistDetailsViewHolder<ArtistDetailsViewData.ReleaseGroupViewData, ListitemReleaseGroupBinding>(
    ListitemReleaseGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {
    override fun bind(data: ArtistDetailsViewData.ReleaseGroupViewData) {
        binding.releaseTitle.text = data.releaseTitle
        binding.releaseType.text = data.releaseType
        binding.releaseDate.text = data.releaseDate
    }
}