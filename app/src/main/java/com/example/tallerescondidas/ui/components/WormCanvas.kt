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
import com.example.tallerescondidas.ui.theme.BrownWood
import com.example.tallerescondidas.ui.theme.Measurement
import com.example.tallerescondidas.ui.theme.DarkText
import com.example.tallerescondidas.ui.theme.WarmOrange
import com.example.tallerescondidas.ui.theme.ForestGreen
import com.example.tallerescondidas.ui.theme.MeadowGreen
import com.example.tallerescondidas.ui.theme.LightMeadowGreen

@Composable
fun WormCanvas(
    modifier: Modifier = Modifier,
    temperature: String = "COLD",
    height: Dp = Measurement.scene
) {
    val peekTarget = when (temperature.uppercase()) {
        "HOT" -> 1f
        "WARM" -> 0.45f
        else -> 0f
    }

    val peek by animateFloatAsState(
        targetValue = peekTarget,
        animationSpec = tween(durationMillis = 400),
        label = "wormPeek"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val width = size.width
        val canvasHeight = size.height

        // Sky
        drawRect(
            brush = Brush.verticalGradient(
                listOf(Color(0xFFBFE3F5), Color(0xFFE6F4E0))
            ),
            size = size
        )

        // Background hill
        drawOval(
            color = MeadowGreen.copy(alpha = 0.55f),
            topLeft = Offset(-width * 0.25f, canvasHeight * 0.32f),
            size = Size(width * 0.9f, canvasHeight * 0.7f)
        )

        // Main hill
        drawOval(
            color = MeadowGreen,
            topLeft = Offset(-width * 0.12f, canvasHeight * 0.45f),
            size = Size(width * 1.24f, canvasHeight * 0.85f)
        )

        // Light on the grass
        drawOval(
            color = LightMeadowGreen.copy(alpha = 0.45f),
            topLeft = Offset(width * 0.05f, canvasHeight * 0.5f),
            size = Size(width * 0.55f, canvasHeight * 0.22f)
        )

        // Burrow
        val burrowCenter = Offset(width * 0.5f, canvasHeight * 0.72f)
        drawOval(
            color = BrownWood.copy(alpha = 0.85f),
            topLeft = Offset(burrowCenter.x - width * 0.11f, burrowCenter.y - canvasHeight * 0.05f),
            size = Size(width * 0.22f, canvasHeight * 0.12f)
        )
        drawOval(
            color = Color(0xFF3B2A18),
            topLeft = Offset(burrowCenter.x - width * 0.085f, burrowCenter.y - canvasHeight * 0.035f),
            size = Size(width * 0.17f, canvasHeight * 0.09f)
        )

        // Worm peaking out of the burrow
        if (peek > 0.02f) {
            drawWorm(
                base = Offset(burrowCenter.x, burrowCenter.y),
                width = width,
                height = canvasHeight,
                peek = peek
            )
        }

        // Bushes in front, for depth
        drawBush(Offset(width * 0.16f, canvasHeight * 0.80f), width * 0.11f)
        drawBush(Offset(width * 0.82f, canvasHeight * 0.76f), width * 0.09f)
        drawBush(Offset(width * 0.62f, canvasHeight * 0.90f), width * 0.13f)
    }
}

private fun DrawScope.drawWorm(
    base: Offset,
    width: Float,
    height: Float,
    peek: Float
) {
    val headRadius = width * 0.07f
    val rise = height * 0.20f * peek
    val headCenter = Offset(base.x, base.y - rise - headRadius * 0.2f)

    // Body
    drawOval(
        color = WarmOrange.copy(alpha = 0.9f),
        topLeft = Offset(base.x - width * 0.045f, headCenter.y),
        size = Size(width * 0.09f, rise + height * 0.04f)
    )

    // Head
    drawCircle(color = WarmOrange, radius = headRadius, center = headCenter)

    // Eyes
    val separation = headRadius * 0.42f
    listOf(-separation, separation).forEach { dx ->
        drawCircle(
            color = Color.White,
            radius = headRadius * 0.26f,
            center = Offset(headCenter.x + dx, headCenter.y - headRadius * 0.12f)
        )
        drawCircle(
            color = DarkText,
            radius = headRadius * 0.12f,
            center = Offset(headCenter.x + dx, headCenter.y - headRadius * 0.12f)
        )
    }

    // Smile
    drawArc(
        color = DarkText,
        startAngle = 20f,
        sweepAngle = 140f,
        useCenter = false,
        topLeft = Offset(headCenter.x - headRadius * 0.4f, headCenter.y + headRadius * 0.05f),
        size = Size(headRadius * 0.8f, headRadius * 0.5f),
        style = Stroke(width = 4f, cap = StrokeCap.Round)
    )
}

private fun DrawScope.drawBush(center: Offset, radius: Float) {
    drawCircle(color = ForestGreen, radius = radius, center = center)
    drawCircle(
        color = ForestGreen,
        radius = radius * 0.8f,
        center = Offset(center.x - radius * 0.85f, center.y + radius * 0.15f)
    )
    drawCircle(
        color = ForestGreen,
        radius = radius * 0.7f,
        center = Offset(center.x + radius * 0.85f, center.y + radius * 0.2f)
    )
    drawCircle(
        color = MeadowGreen.copy(alpha = 0.6f),
        radius = radius * 0.45f,
        center = Offset(center.x - radius * 0.2f, center.y - radius * 0.35f)
    )
}