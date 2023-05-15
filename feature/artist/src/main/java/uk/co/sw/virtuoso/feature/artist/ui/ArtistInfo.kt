package uk.co.sw.virtuoso.feature.artist.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import uk.co.sw.virtuoso.feature.core.ui.theme.VirtuosoTheme

@Composable
fun ArtistInfo(name: String, type: String, duration: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column {
            Text(
                text = name,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    text = type,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = duration,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Preview
@Composable
fun ArtistInfoPreview(){
    VirtuosoTheme {
        ArtistInfo(
            "Artist Name",
            "Artist Type",
            "Artist Duration",
        )
    }
}


//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ArtistInfoNightPreview(){
    VirtuosoTheme {
        ArtistInfo(
            "Artist Name",
            "Artist Type",
            "Artist Duration",
        )
    }
}