package uk.co.sw.virtuoso.feature.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uk.co.sw.virtuoso.feature.artist.adapter.ArtistDetailsAdapter
import uk.co.sw.virtuoso.feature.artist.databinding.FragmentArtistDetailsBinding
import uk.co.sw.virtuoso.feature.artist.viewstate.ArtistDetailsViewState

@AndroidEntryPoint
class ArtistDetailsFragment : Fragment(R.layout.fragment_artist_details) {

    private companion object {
        const val ARTIST_ID_ARG_KEY = "artistId"
    }

    private lateinit var binding: FragmentArtistDetailsBinding
    private val artistId: String by lazy {
        arguments?.getString(ARTIST_ID_ARG_KEY) ?: ""
    }

    private val viewModel: ArtistDetailsViewModel by viewModels()
    private val artistDetailsAdapter = ArtistDetailsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArtistDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.artistDetailsViewState.collectLatest { handleState(it) }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, true) {
            findNavController().navigateUp()
        }

        binding.artistDetailsList.adapter = artistDetailsAdapter
        binding.errorRetryButton.setOnClickListener {
            viewModel.findArtist(artistId)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.findArtist(artistId)
    }

    private fun handleState(viewState: ArtistDetailsViewState) {
        when (viewState) {
            is ArtistDetailsViewState.ArtistDetails -> {
                binding.artistDetailsList.isVisible = true
                binding.progressSpinner.isVisible = false
                binding.errorGroup.isVisible = false

                artistDetailsAdapter.submitList(viewState.viewData)
            }
            ArtistDetailsViewState.Error -> {
                binding.artistDetailsList.isVisible = false
                binding.progressSpinner.isVisible = false
                binding.errorGroup.isVisible = true
            }
            ArtistDetailsViewState.Loading -> {
                binding.artistDetailsList.isVisible = false
                binding.progressSpinner.isVisible = true
                binding.errorGroup.isVisible = false
            }
        }
    }

}