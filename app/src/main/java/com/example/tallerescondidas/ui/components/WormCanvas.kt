package com.example.tallerescondidas.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tallerescondidas.ui.theme.MaderaMarron
import com.example.tallerescondidas.ui.theme.Medida
import com.example.tallerescondidas.ui.theme.TextoOscuro
import com.example.tallerescondidas.ui.theme.TibioNaranja
import com.example.tallerescondidas.ui.theme.VerdeBosque
import com.example.tallerescondidas.ui.theme.VerdePradera
import com.example.tallerescondidas.ui.theme.VerdePraderaClaro


@Composable
fun WormCanvas(
    modifier: Modifier = Modifier,
    temperatura: String = "FRIO",
    altura: Dp = Medida.escenario
) {
    val objetivoAsomo = when (temperatura.uppercase()) {
        "CALIENTE" -> 1f
        "TIBIO" -> 0.45f
        else -> 0f
    }

    val asomo by animateFloatAsState(
        targetValue = objetivoAsomo,
        animationSpec = tween(durationMillis = 400),
        label = "asomoGusanito"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(altura)
    ) {
        val ancho = size.width
        val alto = size.height

        // Cielo
        drawRect(
            brush = Brush.verticalGradient(
                listOf(Color(0xFFBFE3F5), Color(0xFFE6F4E0))
            ),
            size = size
        )

        // Colina del fondo
        drawOval(
            color = VerdePradera.copy(alpha = 0.55f),
            topLeft = Offset(-ancho * 0.25f, alto * 0.32f),
            size = Size(ancho * 0.9f, alto * 0.7f)
        )

        // Colina principal
        drawOval(
            color = VerdePradera,
            topLeft = Offset(-ancho * 0.12f, alto * 0.45f),
            size = Size(ancho * 1.24f, alto * 0.85f)
        )

        // Luz sobre el cesped
        drawOval(
            color = VerdePraderaClaro.copy(alpha = 0.45f),
            topLeft = Offset(ancho * 0.05f, alto * 0.5f),
            size = Size(ancho * 0.55f, alto * 0.22f)
        )

        // Madriguera
        val madrigueraCentro = Offset(ancho * 0.5f, alto * 0.72f)
        drawOval(
            color = MaderaMarron.copy(alpha = 0.85f),
            topLeft = Offset(madrigueraCentro.x - ancho * 0.11f, madrigueraCentro.y - alto * 0.05f),
            size = Size(ancho * 0.22f, alto * 0.12f)
        )
        drawOval(
            color = Color(0xFF3B2A18),
            topLeft = Offset(madrigueraCentro.x - ancho * 0.085f, madrigueraCentro.y - alto * 0.035f),
            size = Size(ancho * 0.17f, alto * 0.09f)
        )

        // Gusanito saliendo de la madriguera
        if (asomo > 0.02f) {
            dibujarGusanito(
                base = Offset(madrigueraCentro.x, madrigueraCentro.y),
                ancho = ancho,
                alto = alto,
                asomo = asomo
            )
        }

        // Matorrales delante, para dar profundidad
        dibujarMatorral(Offset(ancho * 0.16f, alto * 0.80f), ancho * 0.11f)
        dibujarMatorral(Offset(ancho * 0.82f, alto * 0.76f), ancho * 0.09f)
        dibujarMatorral(Offset(ancho * 0.62f, alto * 0.90f), ancho * 0.13f)
    }
}

private fun DrawScope.dibujarGusanito(
    base: Offset,
    ancho: Float,
    alto: Float,
    asomo: Float
) {
    val radioCabeza = ancho * 0.07f
    val subida = alto * 0.20f * asomo
    val centroCabeza = Offset(base.x, base.y - subida - radioCabeza * 0.2f)

    // Cuerpo
    drawOval(
        color = TibioNaranja.copy(alpha = 0.9f),
        topLeft = Offset(base.x - ancho * 0.045f, centroCabeza.y),
        size = Size(ancho * 0.09f, subida + alto * 0.04f)
    )

    // Cabeza
    drawCircle(color = TibioNaranja, radius = radioCabeza, center = centroCabeza)

    // Ojos
    val separacion = radioCabeza * 0.42f
    listOf(-separacion, separacion).forEach { dx ->
        drawCircle(
            color = Color.White,
            radius = radioCabeza * 0.26f,
            center = Offset(centroCabeza.x + dx, centroCabeza.y - radioCabeza * 0.12f)
        )
        drawCircle(
            color = TextoOscuro,
            radius = radioCabeza * 0.12f,
            center = Offset(centroCabeza.x + dx, centroCabeza.y - radioCabeza * 0.12f)
        )
    }

    // Sonrisa
    drawArc(
        color = TextoOscuro,
        startAngle = 20f,
        sweepAngle = 140f,
        useCenter = false,
        topLeft = Offset(centroCabeza.x - radioCabeza * 0.4f, centroCabeza.y + radioCabeza * 0.05f),
        size = Size(radioCabeza * 0.8f, radioCabeza * 0.5f),
        style = Stroke(width = 4f, cap = StrokeCap.Round)
    )
}

private fun DrawScope.dibujarMatorral(centro: Offset, radio: Float) {
    drawCircle(color = VerdeBosque, radius = radio, center = centro)
    drawCircle(
        color = VerdeBosque,
        radius = radio * 0.8f,
        center = Offset(centro.x - radio * 0.85f, centro.y + radio * 0.15f)
    )
    drawCircle(
        color = VerdeBosque,
        radius = radio * 0.7f,
        center = Offset(centro.x + radio * 0.85f, centro.y + radio * 0.2f)
    )
    drawCircle(
        color = VerdePradera.copy(alpha = 0.6f),
        radius = radio * 0.45f,
        center = Offset(centro.x - radio * 0.2f, centro.y - radio * 0.35f)
    )
}