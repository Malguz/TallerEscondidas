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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tallerescondidas.ui.theme.HotRed
import com.example.tallerescondidas.ui.theme.Spacing
import com.example.tallerescondidas.ui.theme.ColdBlue
import com.example.tallerescondidas.ui.theme.Measurement
import com.example.tallerescondidas.ui.theme.CreamPaper
import com.example.tallerescondidas.ui.theme.SoftText
import com.example.tallerescondidas.ui.theme.WarmOrange

@Composable
fun TemperatureIndicator(
    state: String,
    modifier: Modifier = Modifier,
    difference: Double = -1.0
) {
    val color = getTemperatureColor(state)

    val position = if (difference >= 0) {
        // 0 degrees = hot end, 180 degrees = cold end
        (1.0 - (difference / 180.0)).coerceIn(0.05, 0.95).toFloat()
    } else {
        when (state.uppercase()) {
            "HOT" -> 0.88f
            "WARM" -> 0.55f
            else -> 0.15f
        }
    }

    val animatedPosition by animateFloatAsState(
        targetValue = position,
        animationSpec = tween(durationMillis = 300),
        label = "marker"
    )

    val animatedColor by animateColorAsState(
        targetValue = color,
        animationSpec = tween(durationMillis = 300),
        label = "temperatureColor"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(CreamPaper)
            .padding(Spacing.md)
    ) {
        Text(
            text = getStateText(state),
            style = MaterialTheme.typography.headlineMedium,
            color = animatedColor,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Spacing.sm))

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(Measurement.thermometer + 18.dp)
        ) {
            val barHeight = Measurement.thermometer.toPx()
            val y = barHeight / 2f + 6f

            drawRoundRect(
                brush = Brush.horizontalGradient(
                    listOf(ColdBlue, WarmOrange, HotRed)
                ),
                topLeft = Offset(0f, 6f),
                size = Size(size.width, barHeight),
                cornerRadius = CornerRadius(barHeight / 2f)
            )

            val x = size.width * animatedPosition

            drawCircle(color = Color.White, radius = barHeight * 0.78f, center = Offset(x, y))
            drawCircle(color = animatedColor, radius = barHeight * 0.52f, center = Offset(x, y))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Label("Muy frio")
            Label("Tibio")
            Label("Caliente")
        }
    }
}

@Composable
private fun Label(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = SoftText
    )
}

private fun getStateText(state: String): String = when (state.uppercase()) {
    "HOT" -> "Muy cerca"
    "WARM" -> "Te acercas"
    else -> "Estas lejos"
}

fun getTemperatureColor(state: String): Color = when (state.uppercase()) {
    "HOT" -> HotRed
    "WARM" -> WarmOrange
    else -> ColdBlue
}