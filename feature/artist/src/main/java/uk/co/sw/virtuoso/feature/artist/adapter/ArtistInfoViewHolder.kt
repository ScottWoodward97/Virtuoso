package uk.co.sw.virtuoso.feature.artist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import uk.co.sw.virtuoso.feature.artist.databinding.ListitemArtistInfoBinding
import uk.co.sw.virtuoso.feature.artist.viewstate.ArtistDetailsViewData

class ArtistInfoViewHolder(
    parent: ViewGroup
) : ArtistDetailsViewHolder<ArtistDetailsViewData.ArtistInfoViewData, ListitemArtistInfoBinding>(
    ListitemArtistInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {
    override fun bind(data: ArtistDetailsViewData.ArtistInfoViewData) {
        binding.artistName.text = data.artistName
        binding.artistType.text = data.artistType
        binding.artistDuration.text = data.artistDuration
    }
}