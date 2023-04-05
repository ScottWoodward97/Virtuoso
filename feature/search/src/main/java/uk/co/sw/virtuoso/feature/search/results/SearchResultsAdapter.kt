package uk.co.sw.virtuoso.feature.search.results

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uk.co.sw.virtuoso.feature.search.databinding.ListitemSearchResultArtistBinding
import uk.co.sw.virtuoso.feature.search.results.SearchResultsViewState.SearchResults.ArtistResultViewState

class SearchResultsAdapter :
    ListAdapter<ArtistResultViewState, SearchResultsAdapter.SearchResultsViewHolder>(SearchResultsDiff) {

    var onSearchResultClicked: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
        return SearchResultsViewHolder(
            ListitemSearchResultArtistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onSearchResultClicked
        )
    }

    override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class SearchResultsViewHolder(
        private val binding: ListitemSearchResultArtistBinding,
        private val onSearchResultClicked: ((String) -> Unit)?,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ArtistResultViewState) {
            binding.artistName.text = data.name
            binding.resultItem.setOnClickListener {
                onSearchResultClicked?.invoke(data.id)
            }
            binding.artistDisambiguation.isInvisible = data.disambiguation == null
            binding.artistDisambiguation.text = data.disambiguation
        }
    }

    object SearchResultsDiff : DiffUtil.ItemCallback<ArtistResultViewState>() {
        override fun areItemsTheSame(
            oldItem: ArtistResultViewState,
            newItem: ArtistResultViewState
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ArtistResultViewState,
            newItem: ArtistResultViewState
        ): Boolean {
            return oldItem == newItem

        }

    }


}