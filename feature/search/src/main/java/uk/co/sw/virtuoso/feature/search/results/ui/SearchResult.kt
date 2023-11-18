package uk.co.sw.virtuoso.feature.search.results.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.co.sw.virtuoso.feature.core.ui.theme.VirtuosoTheme
import uk.co.sw.virtuoso.feature.search.results.viewstate.SearchResultsViewState.SearchResults.ArtistResultViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResult(
    viewState: ArtistResultViewState,
    onClick: (id: String) -> Unit
) {
    Card(
        modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        shape = ShapeDefaults.ExtraSmall,
        onClick = { onClick(viewState.id) },
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = viewState.name,
            style = MaterialTheme.typography.headlineSmall,
        )
        viewState.disambiguation?.let {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = it,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Preview
@Composable
private fun SearchResultPreview() {
    val viewState = ArtistResultViewState("id", "Name", "Description")
    VirtuosoTheme {
        SearchResult(viewState){}
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SearchResultNightPreview() {
    val viewState = ArtistResultViewState("id", "Name", "Description")
    VirtuosoTheme {
        SearchResult(viewState){}
    }
}