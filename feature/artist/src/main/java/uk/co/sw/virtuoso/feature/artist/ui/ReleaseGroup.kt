package uk.co.sw.virtuoso.feature.artist.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.co.sw.virtuoso.feature.core.ui.theme.VirtuosoTheme

@Composable
fun ReleaseGroup(title: String, type: String, date: String){
    Surface(
        modifier = Modifier
            .padding(horizontal = 0.dp, vertical = 4.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 8.dp, vertical = 0.dp)
            .fillMaxWidth()
    ) {
        Column() {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = type,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = date,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

//@Preview
@Composable
fun ReleaseGroupPreview(){
    VirtuosoTheme {
        ReleaseGroup("Title", "Type", "Date")
    }
}

//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReleaseGroupNightPreview(){
    VirtuosoTheme {
        ReleaseGroup("Title", "Type", "Date")
    }
}