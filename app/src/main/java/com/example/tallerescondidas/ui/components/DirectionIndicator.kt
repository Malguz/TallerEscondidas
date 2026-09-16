package com.example.tallerescondidas.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.example.tallerescondidas.ui.theme.Espacio
import com.example.tallerescondidas.ui.theme.Medida
import com.example.tallerescondidas.ui.theme.PapelCrema
import com.example.tallerescondidas.ui.theme.PapelSombra
import com.example.tallerescondidas.ui.theme.TextoSuave
import com.example.tallerescondidas.ui.theme.TibioNaranja
import com.example.tallerescondidas.ui.theme.VerdeBosque
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun DirectionIndicator(
    direccion: Float,
    modifier: Modifier = Modifier,
    colorGuia: Color = TibioNaranja
) {

    val anguloContinuo = remember { mutableFloatStateOf(0f) }

    LaunchedEffect(direccion) {
        anguloContinuo.floatValue += diferenciaCorta(direccion - anguloContinuo.floatValue)
    }

    val anguloAnimado by animateFloatAsState(
        targetValue = anguloContinuo.floatValue,
        animationSpec = tween(durationMillis = 220),
        label = "aguja"
    )

    Box(
        modifier = modifier.size(Medida.brujula),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centro = Offset(size.width / 2f, size.height / 2f)
            val radio = size.minDimension * 0.40f


            drawCircle(color = PapelCrema, radius = radio * 1.18f, center = centro)
            drawCircle(
                color = PapelSombra,
                radius = radio * 1.18f,
                center = centro,
                style = Stroke(width = 3f)
            )


            drawCircle(color = Color.White.copy(alpha = 0.55f), radius = radio, center = centro)


            for (i in 0 until 12) {
                val cardinal = i % 3 == 0
                val largo = if (cardinal) radio * 0.16f else radio * 0.08f
                val rad = Math.toRadians((i * 30).toDouble())
                val dx = sin(rad).toFloat()
                val dy = -cos(rad).toFloat()

                drawLine(
                    color = if (cardinal) VerdeBosque else PapelSombra,
                    start = Offset(centro.x + dx * (radio - largo), centro.y + dy * (radio - largo)),
                    end = Offset(centro.x + dx * radio, centro.y + dy * radio),
                    strokeWidth = if (cardinal) 5f else 3f,
                    cap = StrokeCap.Round
                )
            }

            rotate(degrees = anguloAnimado, pivot = centro) {
                dibujarCono(centro, radio * 0.92f, colorGuia)
            }


            rotate(degrees = anguloAnimado, pivot = centro) {
                val punta = Offset(centro.x, centro.y - radio * 0.78f)
                val aguja = Path().apply {
                    moveTo(punta.x, punta.y)
                    lineTo(centro.x - radio * 0.12f, centro.y + radio * 0.10f)
                    lineTo(centro.x + radio * 0.12f, centro.y + radio * 0.10f)
                    close()
                }
                drawPath(path = aguja, color = colorGuia)
            }


            drawCircle(color = PapelCrema, radius = radio * 0.11f, center = centro)
            drawCircle(
                color = VerdeBosque,
                radius = radio * 0.11f,
                center = centro,
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
private fun androidx.compose.foundation.layout.BoxScope.Cardinal(
    letra: String,
    posicion: Alignment
) {
    Text(
        text = letra,
        style = MaterialTheme.typography.labelLarge,
        color = TextoSuave,
        modifier = Modifier
            .align(posicion)
            .padding(Espacio.xs)
    )
}


private fun DrawScope.dibujarCono(centro: Offset, radio: Float, color: Color) {
    val apertura = 60f
    val rect = androidx.compose.ui.geometry.Rect(
        offset = Offset(centro.x - radio, centro.y - radio),
        size = Size(radio * 2, radio * 2)
    )

    val cono = Path().apply {
        moveTo(centro.x, centro.y)
        arcTo(
            rect = rect,
            startAngleDegrees = -90f - apertura / 2f,
            sweepAngleDegrees = apertura,
            forceMoveTo = false
        )
        close()
    }

    drawPath(path = cono, color = color.copy(alpha = 0.22f))
}

/** Reduce cualquier diferencia de angulo al rango -180..180. */
private fun diferenciaCorta(grados: Float): Float {
    var valor = grados % 360f
    if (valor > 180f) valor -= 360f
    if (valor < -180f) valor += 360f
    return valor
}