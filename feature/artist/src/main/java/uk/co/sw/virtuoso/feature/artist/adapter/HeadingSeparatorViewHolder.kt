package uk.co.sw.virtuoso.feature.artist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import uk.co.sw.virtuoso.feature.artist.databinding.ListitemHeadingSeparatorBinding
import uk.co.sw.virtuoso.feature.artist.viewstate.ArtistDetailsViewData

class HeadingSeparatorViewHolder(
    parent: ViewGroup
) : ArtistDetailsViewHolder<ArtistDetailsViewData.HeadingSeparatorViewData, ListitemHeadingSeparatorBinding>(
    ListitemHeadingSeparatorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {
    override fun bind(data: ArtistDetailsViewData.HeadingSeparatorViewData) {
        binding.heading.text = data.title
    }

}