package uk.co.sw.virtuoso.feature.artist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.co.sw.virtuoso.feature.artist.ArtistDetailsViewModel
import uk.co.sw.virtuoso.feature.artist.viewstate.ArtistDetailsViewData
import uk.co.sw.virtuoso.feature.artist.viewstate.ArtistDetailsViewState
import uk.co.sw.virtuoso.feature.core.ui.theme.VirtuosoTheme

@Composable
fun ArtistDetailsScreen(artistId: String, viewModel: ArtistDetailsViewModel) {

    val viewState = viewModel.artistDetailsViewState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (val state = viewState.value) {
            is ArtistDetailsViewState.ArtistDetails -> ArtistDetails(state.viewData)
            ArtistDetailsViewState.Error -> ArtistError { viewModel.findArtist(artistId) }
            ArtistDetailsViewState.Loading -> ArtistLoading()
        }
    }
}

@Composable
private fun ArtistError(onRetryClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(
            text = stringResource(
                id = uk.co.sw.virtuoso.feature.core.R.string.generic_error_message
            ),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Button(
            colors = ButtonDefaults.textButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            onClick = onRetryClick,
        ) {
            Text(
                text = stringResource(
                    id = uk.co.sw.virtuoso.feature.core.R.string.retry_button_cta
                ),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

//@Preview
@Composable
fun ErrorPreview(){
    VirtuosoTheme {
        ArtistError {}
    }
}

@Composable
private fun ArtistLoading() {
    CircularProgressIndicator(
        modifier = Modifier.wrapContentSize(),
        color = MaterialTheme.colorScheme.secondary,
    )
}

//@Preview
@Composable
fun LoadingPreview(){
    VirtuosoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ArtistLoading()
        }
    }
}

@Composable
private fun ArtistDetails(viewData: List<ArtistDetailsViewData>) {
    LazyColumn(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = 8.dp, vertical = 0.dp)
    ){
        items(viewData){
            when (it) {
                is ArtistDetailsViewData.ArtistInfoViewData ->
                    ArtistInfo(
                        name = it.artistName,
                        type = it.artistType,
                        duration = it.artistDuration,
                    )
                is ArtistDetailsViewData.HeadingSeparatorViewData ->
                    HeadingSeparator(title = it.title)
                is ArtistDetailsViewData.ReleaseGroupViewData ->
                    ReleaseGroup(
                        title = it.releaseTitle,
                        type = it.releaseType,
                        date = it.releaseDate,
                    )
            }
        }
    }
}

//@Preview
@Composable
fun DetailsPreview(){
    val viewData = listOf(
        ArtistDetailsViewData.ArtistInfoViewData("Name", "Type", "Duration"),
        ArtistDetailsViewData.HeadingSeparatorViewData("Releases"),
        ArtistDetailsViewData.ReleaseGroupViewData("Title", "Type", "Date"),
        ArtistDetailsViewData.ReleaseGroupViewData("Title", "Type", "Date"),
        ArtistDetailsViewData.ReleaseGroupViewData("Title", "Type", "Date"),
    )
    VirtuosoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ){
            ArtistDetails(viewData = viewData)
        }
    }
}