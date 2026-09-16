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


private val EsquemaJuego = lightColorScheme(
    primary = VerdeBosque,
    onPrimary = TextoSobreVerde,
    primaryContainer = VerdePradera,
    onPrimaryContainer = TextoSobreVerde,

    secondary = TibioNaranja,
    onSecondary = TextoOscuro,
    secondaryContainer = TibioNaranjaClaro,
    onSecondaryContainer = TextoOscuro,

    tertiary = MaderaMarron,
    onTertiary = PapelCrema,

    background = VerdeCesped,
    onBackground = TextoOscuro,

    surface = PapelCrema,
    onSurface = TextoOscuro,
    surfaceVariant = VerdeNiebla,
    onSurfaceVariant = TextoSuave,

    error = CalienteRojo,
    onError = PapelCrema,
    outline = PapelSombra
)


private val FormasJuego = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(14.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(28.dp),
    extraLarge = RoundedCornerShape(36.dp)
)

@Composable
fun TallerEscondidasTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val vista = LocalView.current

    if (!vista.isInEditMode) {
        SideEffect {
            val ventana = (vista.context as Activity).window
            ventana.statusBarColor = VerdeBosque.toArgb()
            WindowCompat.getInsetsController(ventana, vista)
                .isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = EsquemaJuego,
        typography = Typography,
        shapes = FormasJuego,
        content = content
    )
}