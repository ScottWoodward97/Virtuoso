package uk.co.sw.virtuoso.feature.artist.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import uk.co.sw.virtuoso.feature.artist.viewstate.ArtistDetailsViewData

abstract class ArtistDetailsViewHolder<DATA : ArtistDetailsViewData, VB : ViewBinding>(
    protected val binding: VB
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(data: DATA)

}