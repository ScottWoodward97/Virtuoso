package uk.co.sw.virtuoso.feature.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uk.co.sw.virtuoso.feature.artist.ui.ArtistDetailsScreen
import uk.co.sw.virtuoso.feature.core.ui.theme.VirtuosoTheme

@AndroidEntryPoint
class ArtistDetailsFragment : Fragment() {

    private companion object {
        const val ARTIST_ID_ARG_KEY = "artistId"
    }

    private val artistId: String by lazy {
        arguments?.getString(ARTIST_ID_ARG_KEY) ?: ""
    }

    private val viewModel: ArtistDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                VirtuosoTheme {
                    ArtistDetailsScreen(artistId = artistId, viewModel = viewModel)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, true) {
            findNavController().navigateUp()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.findArtist(artistId)
    }

}