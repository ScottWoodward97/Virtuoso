package uk.co.sw.virtuoso.feature.search.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import uk.co.sw.virtuoso.feature.core.ui.states.RetryErrorMessage
import uk.co.sw.virtuoso.feature.core.ui.theme.VirtuosoTheme
import uk.co.sw.virtuoso.feature.search.R
import uk.co.sw.virtuoso.feature.search.SearchViewModel
import uk.co.sw.virtuoso.feature.search.results.ui.SearchResult
import uk.co.sw.virtuoso.feature.search.results.viewstate.SearchResultsViewState
import uk.co.sw.virtuoso.feature.search.results.viewstate.SearchResultsViewState.SearchResults.ArtistResultViewState

@Composable
fun SearchResultsScreen(
    onResultClicked: (String) -> Unit,
    viewModel: SearchViewModel,
) {

    val viewState = viewModel.searchResultsViewState.collectAsState()
    SearchUI(
        viewState = viewState.value,
        onSearch = viewModel::searchArtist,
        onResultClicked = onResultClicked
    )
}

@Composable
private fun SearchUI(
    viewState: SearchResultsViewState,
    onSearch: (String) -> Unit,
    onResultClicked: (String) -> Unit,
) {
    val searchTerm = rememberSaveable { mutableStateOf<String>("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = searchTerm.value,
                onValueChange = {
                    searchTerm.value = it
                    onSearch(searchTerm.value)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = { onSearch(searchTerm.value) }
                )
            )
            Box {
                when (viewState) {
                    SearchResultsViewState.Error -> SearchError {
                        onSearch(searchTerm.value)
                    }
                    SearchResultsViewState.Loading -> SearchLoading()
                    SearchResultsViewState.NoResults -> SearchEmpty()
                    is SearchResultsViewState.SearchResults -> SearchResults(
                        viewState.results,
                        onResultClicked
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchError(onRetryClick: () -> Unit) {
    RetryErrorMessage(
        title = stringResource(
            id = uk.co.sw.virtuoso.feature.core.R.string.generic_error_message
        ),
        actionMessage = stringResource(
            id = uk.co.sw.virtuoso.feature.core.R.string.retry_button_cta
        ),
        onActionClicked = onRetryClick
    )
}

@Composable
private fun SearchLoading() {
    CircularProgressIndicator(
        modifier = Modifier.wrapContentSize(),
        color = MaterialTheme.colorScheme.secondary,
    )
}

@Composable
private fun SearchEmpty() {
    Text(
        text = stringResource(id = R.string.no_results_message),
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onBackground,
    )
}

@Composable
private fun SearchResults(
    results: List<ArtistResultViewState>,
    onResultClicked: (String) -> Unit
) {
    LazyColumn {
        items(results) { viewState ->
            SearchResult(viewState = viewState, onClick = onResultClicked)
        }
    }
}

@Preview
@Composable
private fun ScreenUILoadingPreview(){
    val viewState = SearchResultsViewState.Loading
    VirtuosoTheme {
        SearchUI(
            viewState = viewState,
            onSearch = {},
            onResultClicked = {},
        )
    }
}

@Preview
@Composable
private fun ScreenUINoResultsPreview(){
    val viewState = SearchResultsViewState.NoResults
    VirtuosoTheme {
        SearchUI(
            viewState = viewState,
            onSearch = {},
            onResultClicked = {},
        )
    }
}

@Preview
@Composable
private fun ScreenUIErrorPreview(){
    val viewState = SearchResultsViewState.Error
    VirtuosoTheme {
        SearchUI(
            viewState = viewState,
            onSearch = {},
            onResultClicked = {},
        )
    }
}

@Preview
@Composable
private fun ScreenUIResultsPreview(){
    val result = ArtistResultViewState("1", "Artist", "Disambiguation")
    val viewState = SearchResultsViewState.SearchResults(3, listOf(result, result, result))
    VirtuosoTheme {
        SearchUI(
            viewState = viewState,
            onSearch = {},
            onResultClicked = {},
        )
    }
}

