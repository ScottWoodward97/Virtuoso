package uk.co.sw.virtuoso.feature.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import uk.co.sw.virtuoso.feature.core.R

private val virtuosoFontFamily = FontFamily(
    Font(R.font.dosis_extra_light, weight = FontWeight.ExtraLight),
    Font(R.font.dosis_light, weight = FontWeight.Light),
    Font(R.font.dosis_regular, weight = FontWeight.Normal),
    Font(R.font.dosis_medium, weight = FontWeight.Medium),
    Font(R.font.dosis_semi_bold, weight = FontWeight.SemiBold),
    Font(R.font.dosis_bold, weight = FontWeight.Bold),
    Font(R.font.dosis_extra_bold, weight = FontWeight.ExtraBold),
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = virtuosoFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = virtuosoFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = virtuosoFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp
    ),
    displaySmall = TextStyle(
        fontFamily = virtuosoFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = virtuosoFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
)