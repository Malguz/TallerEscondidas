package com.example.tallerescondidas.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.example.tallerescondidas.ui.theme.Spacing
import com.example.tallerescondidas.ui.theme.Measurement
import com.example.tallerescondidas.ui.theme.CreamPaper
import com.example.tallerescondidas.ui.theme.ShadowPaper
import com.example.tallerescondidas.ui.theme.SoftText
import com.example.tallerescondidas.ui.theme.WarmOrange
import com.example.tallerescondidas.ui.theme.ForestGreen
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun DirectionIndicator(
    direction: Float,
    modifier: Modifier = Modifier,
    guideColor: Color = WarmOrange
) {
    val continuousAngle = remember { mutableFloatStateOf(0f) }

    LaunchedEffect(direction) {
        continuousAngle.floatValue += shortDifference(direction - continuousAngle.floatValue)
    }

    val animatedAngle by animateFloatAsState(
        targetValue = continuousAngle.floatValue,
        animationSpec = tween(durationMillis = 220),
        label = "needle"
    )

    Box(
        modifier = modifier.size(Measurement.compass),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radius = size.minDimension * 0.40f

            drawCircle(color = CreamPaper, radius = radius * 1.18f, center = center)
            drawCircle(
                color = ShadowPaper,
                radius = radius * 1.18f,
                center = center,
                style = Stroke(width = 3f)
            )




            drawCircle(color = Color.White.copy(alpha = 0.55f), radius = radius, center = center)

            for (i in 0 until 12) {
                val cardinal = i % 3 == 0
                val length = if (cardinal) radius * 0.16f else radius * 0.08f
                val rad = Math.toRadians((i * 30).toDouble())
                val dx = sin(rad).toFloat()
                val dy = -cos(rad).toFloat()

                drawLine(
                    color = if (cardinal) ForestGreen else ShadowPaper,
                    start = Offset(center.x + dx * (radius - length), center.y + dy * (radius - length)),
                    end = Offset(center.x + dx * radius, center.y + dy * radius),
                    strokeWidth = if (cardinal) 5f else 3f,
                    cap = StrokeCap.Round
                )
            }

            rotate(degrees = animatedAngle, pivot = center) {
                drawCone(center, radius * 0.92f, guideColor)
            }


            rotate(degrees = animatedAngle, pivot = center) {
                val tip = Offset(center.x, center.y - radius * 0.78f)
                val needle = Path().apply {
                    moveTo(tip.x, tip.y)
                    lineTo(center.x - radius * 0.12f, center.y + radius * 0.10f)
                    lineTo(center.x + radius * 0.12f, center.y + radius * 0.10f)
                    close()
                }
                drawPath(path = needle, color = guideColor)
            }


            drawCircle(color = CreamPaper, radius = radius * 0.11f, center = center)
            drawCircle(
                color = ForestGreen,
                radius = radius * 0.11f,
                center = center,
                style = Stroke(width = 4f)
            )
        }

        Cardinal("N", Alignment.TopCenter)
        Cardinal("E", Alignment.CenterEnd)
        Cardinal("S", Alignment.BottomCenter)
        Cardinal("O", Alignment.CenterStart)
    }
}

@Composable
private fun BoxScope.Cardinal(
    letter: String,
    position: Alignment
) {
    Text(
        text = letter,
        style = MaterialTheme.typography.labelLarge,
        color = SoftText,
        modifier = Modifier
            .align(position)
            .padding(Spacing.xs)
    )
}


private fun DrawScope.drawCone(center: Offset, radius: Float, color: Color) {
    val aperture = 60f
    val rect = Rect(
        offset = Offset(center.x - radius, center.y - radius),
        size = Size(radius * 2, radius * 2)
    )

    val cone = Path().apply {
        moveTo(center.x, center.y)
        arcTo(
            rect = rect,
            startAngleDegrees = -90f - aperture / 2f,
            sweepAngleDegrees = aperture,
            forceMoveTo = false
        )
        close()
    }

    drawPath(path = cone, color = color.copy(alpha = 0.22f))
}

/** Reduces any angle difference to the range -180..180. */
private fun shortDifference(degrees: Float): Float {
    var value = degrees % 360f
    if (value > 180f) value -= 360f
    if (value < -180f) value += 360f
    return value
}