package com.example.tallerescondidas.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tallerescondidas.ui.theme.CalienteRojo
import com.example.tallerescondidas.ui.theme.Espacio
import com.example.tallerescondidas.ui.theme.FrioAzul
import com.example.tallerescondidas.ui.theme.Medida
import com.example.tallerescondidas.ui.theme.PapelCrema
import com.example.tallerescondidas.ui.theme.TextoSuave
import com.example.tallerescondidas.ui.theme.TibioNaranja


@Composable
fun TemperatureIndicator(
    estado: String,
    modifier: Modifier = Modifier,
    diferencia: Double = -1.0
) {
    val color = colorDeTemperatura(estado)

    val posicion = if (diferencia >= 0) {
        // 0 grados = extremo caliente, 180 grados = extremo frio
        (1.0 - (diferencia / 180.0)).coerceIn(0.05, 0.95).toFloat()
    } else {
        when (estado.uppercase()) {
            "CALIENTE" -> 0.88f
            "TIBIO" -> 0.55f
            else -> 0.15f
        }
    }

    val posicionAnimada by animateFloatAsState(
        targetValue = posicion,
        animationSpec = tween(durationMillis = 300),
        label = "marcador"
    )

    val colorAnimado by animateColorAsState(
        targetValue = color,
        animationSpec = tween(durationMillis = 300),
        label = "colorTemperatura"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(PapelCrema)
            .padding(Espacio.md)
    ) {
        Text(
            text = textoDeEstado(estado),
            style = MaterialTheme.typography.headlineMedium,
            color = colorAnimado,
            modifier = Modifier.fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Espacio.sm))

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(Medida.termometro + 18.dp)
        ) {
            val alturaBarra = Medida.termometro.toPx()
            val y = alturaBarra / 2f + 6f

            drawRoundRect(
                brush = Brush.horizontalGradient(
                    listOf(FrioAzul, TibioNaranja, CalienteRojo)
                ),
                topLeft = Offset(0f, 6f),
                size = androidx.compose.ui.geometry.Size(size.width, alturaBarra),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(alturaBarra / 2f)
            )

            val x = size.width * posicionAnimada

            drawCircle(color = Color.White, radius = alturaBarra * 0.78f, center = Offset(x, y))
            drawCircle(color = colorAnimado, radius = alturaBarra * 0.52f, center = Offset(x, y))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Etiqueta("Muy frio")
            Etiqueta("Tibio")
            Etiqueta("Caliente")
        }
    }
}

@Composable
private fun Etiqueta(texto: String) {
    Text(
        text = texto,
        style = MaterialTheme.typography.labelSmall,
        color = TextoSuave
    )
}

private fun textoDeEstado(estado: String): String = when (estado.uppercase()) {
    "CALIENTE" -> "Muy cerca"
    "TIBIO" -> "Te acercas"
    else -> "Estas lejos"
}

fun colorDeTemperatura(estado: String): Color = when (estado.uppercase()) {
    "CALIENTE" -> CalienteRojo
    "TIBIO" -> TibioNaranja
    else -> FrioAzul
}