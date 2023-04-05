package uk.co.sw.virtuoso.feature.artist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import uk.co.sw.virtuoso.feature.artist.viewstate.ArtistDetailsViewData

class ArtistDetailsAdapter :
    ListAdapter<ArtistDetailsViewData, ArtistDetailsViewHolder<*, *>>(ArtistDetailsDiff) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ArtistDetailsViewData.ArtistInfoViewData -> ArtistDetailsViewData.ViewType.ARTIST_INFO.ordinal
            is ArtistDetailsViewData.HeadingSeparatorViewData -> ArtistDetailsViewData.ViewType.HEADING_SEPARATOR.ordinal
            is ArtistDetailsViewData.ReleaseGroupViewData -> ArtistDetailsViewData.ViewType.RELEASE_GROUP.ordinal
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtistDetailsViewHolder<*, *> {
        return when (ArtistDetailsViewData.ViewType.values()[viewType]) {
            ArtistDetailsViewData.ViewType.ARTIST_INFO -> ArtistInfoViewHolder(parent)
            ArtistDetailsViewData.ViewType.HEADING_SEPARATOR -> HeadingSeparatorViewHolder(parent)
            ArtistDetailsViewData.ViewType.RELEASE_GROUP -> ReleaseGroupViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: ArtistDetailsViewHolder<*, *>, position: Int) {
        when (val item = getItem(position)) {
            is ArtistDetailsViewData.ArtistInfoViewData ->
                (holder as ArtistInfoViewHolder).bind(item)
            is ArtistDetailsViewData.HeadingSeparatorViewData ->
                (holder as HeadingSeparatorViewHolder).bind(item)
            is ArtistDetailsViewData.ReleaseGroupViewData ->
                (holder as ReleaseGroupViewHolder).bind(item)
        }
    }

    private object ArtistDetailsDiff : DiffUtil.ItemCallback<ArtistDetailsViewData>() {
        override fun areItemsTheSame(
            oldItem: ArtistDetailsViewData,
            newItem: ArtistDetailsViewData
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ArtistDetailsViewData,
            newItem: ArtistDetailsViewData
        ): Boolean {
            return oldItem == newItem
        }
    }

}