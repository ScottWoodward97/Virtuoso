package uk.co.sw.virtuoso.feature.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uk.co.sw.virtuoso.feature.core.ui.theme.VirtuosoTheme
import uk.co.sw.virtuoso.feature.search.ui.SearchResultsScreen

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private companion object {
        const val ARTIST_DEEPLINK_URI = "virtuoso://artist/%s"
    }

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                VirtuosoTheme {
                    SearchResultsScreen(
                        viewModel = viewModel,
                        onResultClicked = ::onSearchResultClicked,
                    )
                }
            }
        }
    }

    private fun onSearchResultClicked(artistId: String) {
        findNavController().navigate(
            NavDeepLinkRequest.Builder.fromUri(
                ARTIST_DEEPLINK_URI.format(artistId).toUri()
            ).build()
        )
    }
}