package uk.co.sw.virtuoso.feature.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import uk.co.sw.virtuoso.feature.search.databinding.FragmentSearchBinding
import uk.co.sw.virtuoso.feature.search.results.SearchResultsAdapter
import uk.co.sw.virtuoso.feature.search.results.SearchResultsViewState

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private companion object {
        const val ARTIST_DEEPLINK_URI = "virtuoso://artist/%s"
    }

    private lateinit var binding: FragmentSearchBinding

    private val viewModel: SearchViewModel by viewModels()
    private val adapter = SearchResultsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.searchResultsViewState.collectLatest { handleState(it) }
        }
        binding.searchResultsArtists.adapter = adapter
        adapter.onSearchResultClicked = {
            findNavController().navigate(
                NavDeepLinkRequest.Builder.fromUri(ARTIST_DEEPLINK_URI.format(it).toUri()).build()
            )
        }
        binding.searchInputEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.searchArtist(text?.toString() ?: "")
        }
        binding.searchInputEditText.setOnEditorActionListener { textView, id, _ ->
            when (id) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    viewModel.searchArtist(textView.text.toString())
                    binding.searchInputLayout.clearFocus()
                    (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
                        ?.hideSoftInputFromWindow(binding.searchInputLayout.windowToken, 0)
                    true
                }
                else -> false
            }
        }

        binding.errorRetryButton.setOnClickListener {
            viewModel.searchArtist(binding.searchInputEditText.text?.toString() ?: "")
        }
    }

    private fun handleState(viewState: SearchResultsViewState) {
        when (viewState) {
            is SearchResultsViewState.SearchResults -> {
                binding.searchResultsArtists.isVisible = true
                binding.noResultsMessage.isVisible = false
                binding.progressSpinner.isVisible = false
                binding.errorGroup.isVisible = false

                adapter.submitList(viewState.results)
            }
            SearchResultsViewState.NoResults -> {
                binding.searchResultsArtists.isVisible = false
                binding.noResultsMessage.isVisible = true
                binding.progressSpinner.isVisible = false
                binding.errorGroup.isVisible = false

                adapter.submitList(emptyList())
            }
            SearchResultsViewState.Error -> {
                binding.searchResultsArtists.isVisible = false
                binding.noResultsMessage.isVisible = false
                binding.progressSpinner.isVisible = false
                binding.errorGroup.isVisible = true
            }
            SearchResultsViewState.Loading -> {
                binding.searchResultsArtists.isVisible = false
                binding.noResultsMessage.isVisible = false
                binding.progressSpinner.isVisible = true
                binding.errorGroup.isVisible = false
            }
        }
    }

}