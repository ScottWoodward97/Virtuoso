package uk.co.sw.virtuoso.feature.artist.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
fun HeadingSeparator(title: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Text(
            text = title,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
        )
    }
}

//@Preview
@Composable
fun HeadingPreview(){
    VirtuosoTheme {
        HeadingSeparator("Heading")
    }
}
//@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HeadingNightPreview(){
    VirtuosoTheme {
        HeadingSeparator("Heading")
    }
}