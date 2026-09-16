package com.example.tallerescondidas.ui.theme

import android.app.Activity
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat


private val GameScheme = lightColorScheme(
    primary = ForestGreen,
    onPrimary = TextOnGreen,
    primaryContainer = MeadowGreen,
    onPrimaryContainer = TextOnGreen,

    secondary = WarmOrange,
    onSecondary = DarkText,
    secondaryContainer = LightWarmOrange,
    onSecondaryContainer = DarkText,

    tertiary = BrownWood,
    onTertiary = CreamPaper,

    background = GrassGreen,
    onBackground = DarkText,

    surface = CreamPaper,
    onSurface = DarkText,
    surfaceVariant = MistGreen,
    onSurfaceVariant = SoftText,

    error = HotRed,
    onError = CreamPaper,
    outline = ShadowPaper
)


private val GameShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(14.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(28.dp),
    extraLarge = RoundedCornerShape(36.dp)
)

@Composable
fun HiddenWorkshopTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = ForestGreen.toArgb()
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = GameScheme,
        typography = Typography,
        shapes = GameShapes,
        content = content
    )
}