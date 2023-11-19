package uk.co.sw.virtuoso

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uk.co.sw.virtuoso.feature.artist.ui.ArtistDetailsScreen
import uk.co.sw.virtuoso.feature.search.ui.SearchResultsScreen

@Composable
fun VirtuosoNavHost(navController: NavHostController = rememberNavController()){
    NavHost(navController = navController, startDestination = NavHostRoutes.SEARCH_ROUTE) {
        composable(NavHostRoutes.SEARCH_ROUTE) {
            SearchResultsScreen(
                onResultClicked = { id -> navController.navigate(NavHostRoutes.createArtistRoute(id)) },
                viewModel = hiltViewModel(),
            )
        }
        composable(
            NavHostRoutes.ARTIST_ROUTE,
            arguments = listOf(navArgument(NavHostRoutes.ARTIST_ROUTE_ID_ARG) { type = NavType.StringType })
        ) {
            ArtistDetailsScreen(viewModel = hiltViewModel())
        }
    }

}

object NavHostRoutes {
    const val SEARCH_ROUTE = "search"
    private const val ARTIST_BASE_ROUTE = "artist"
    const val ARTIST_ROUTE_ID_ARG = "artistID"
    const val ARTIST_ROUTE = "$ARTIST_BASE_ROUTE/{$ARTIST_ROUTE_ID_ARG}"

    fun createArtistRoute(artistID: String): String {
        return "$ARTIST_BASE_ROUTE/$artistID"
    }
}