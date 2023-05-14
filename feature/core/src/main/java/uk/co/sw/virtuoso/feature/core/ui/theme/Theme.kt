package uk.co.sw.virtuoso.feature.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val darkColors = darkColorScheme(
    primary = White,
    onPrimary = Black,
    secondary = Orange_600,
    onSecondary = Black,
    background = Black,
    onBackground = White,
    surface = Grey_800,
    onSurface = White,
)
private val lightColors = lightColorScheme(
    primary = Black,
    onPrimary = White,
    secondary = Orange_600,
    onSecondary = Black,
    background = Grey_200,
    onBackground = Black,
    surface = White,
    onSurface = Black,
)

@Composable
fun VirtuosoTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) darkColors else lightColors
    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController, darkTheme) {
        systemUiController.setSystemBarsColor(
            color = Black,
            darkIcons = darkTheme
        )
        onDispose {}
    }
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content,
    )
}