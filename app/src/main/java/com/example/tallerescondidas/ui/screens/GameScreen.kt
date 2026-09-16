package com.example.tallerescondidas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tallerescondidas.ui.components.BotonJuegoSecundario
import com.example.tallerescondidas.ui.components.DirectionIndicator
import com.example.tallerescondidas.ui.components.TemperatureIndicator
import com.example.tallerescondidas.ui.components.WormCanvas
import com.example.tallerescondidas.ui.components.colorDeTemperatura
import com.example.tallerescondidas.ui.theme.AmarilloEstrella
import com.example.tallerescondidas.ui.theme.CalienteRojo
import com.example.tallerescondidas.ui.theme.Espacio
import com.example.tallerescondidas.ui.theme.PapelCrema
import com.example.tallerescondidas.ui.theme.TextoOscuro
import com.example.tallerescondidas.ui.theme.TextoSuave
import com.example.tallerescondidas.ui.theme.VerdeBosque
import com.example.tallerescondidas.ui.theme.VerdeCesped

@Composable
fun GameScreen(
    tiempoRestante: Int,
    puntaje: Int,
    temperatura: String,
    diferencia: Double,
    azimuth: Float,
    direccionObjetivo: Float,
    onReiniciarClick: () -> Unit
) {
    val colorActual = colorDeTemperatura(temperatura)
    val quedaPocoTiempo = tiempoRestante <= 10

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VerdeCesped)
            .verticalScroll(rememberScrollState())
            .padding(Espacio.md),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Marcador: tiempo a la izquierda, puntos a la derecha
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Espacio.sm)
        ) {
            Marcador(
                etiqueta = "Tiempo",
                valor = formatoTiempo(tiempoRestante),
                color = if (quedaPocoTiempo) CalienteRojo else TextoOscuro,
                modifier = Modifier.weight(1f)
            )

            Marcador(
                etiqueta = "Puntos",
                valor = puntaje.toString(),
                color = TextoOscuro,
                icono = true,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(Espacio.sm))

        // Instruccion permanente
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.small)
                .background(PapelCrema)
                .padding(Espacio.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = VerdeBosque,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(Espacio.sm))
            Text(
                text = "Gira el telefono hasta que la barra llegue a caliente",
                style = MaterialTheme.typography.bodyMedium,
                color = TextoSuave
            )
        }

        Spacer(modifier = Modifier.height(Espacio.md))

        // Escenario con la brujula encima
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large),
            contentAlignment = Alignment.Center
        ) {
            WormCanvas(
                temperatura = temperatura,
                altura = 300.dp
            )

            DirectionIndicator(
                direccion = direccionObjetivo - azimuth,
                colorGuia = colorActual
            )
        }

        Spacer(modifier = Modifier.height(Espacio.md))

        TemperatureIndicator(
            estado = temperatura,
            diferencia = diferencia
        )

        Spacer(modifier = Modifier.height(Espacio.sm))

        Text(
            text = "Te faltan ${diferencia.toInt()} grados",
            style = MaterialTheme.typography.labelSmall,
            color = TextoSuave
        )

        Spacer(modifier = Modifier.height(Espacio.md))

        BotonJuegoSecundario(
            texto = "Reiniciar partida",
            icono = Icons.Default.Refresh,
            onClick = onReiniciarClick
        )

        Spacer(modifier = Modifier.height(Espacio.md))
    }
}

@Composable
private fun Marcador(
    etiqueta: String,
    valor: String,
    color: Color,
    modifier: Modifier = Modifier,
    icono: Boolean = false
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .background(PapelCrema)
            .padding(vertical = Espacio.sm, horizontal = Espacio.md)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icono) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = AmarilloEstrella,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(Espacio.xs))
            }
            Text(
                text = etiqueta,
                style = MaterialTheme.typography.labelSmall,
                color = TextoSuave
            )
        }

        Text(
            text = valor,
            style = MaterialTheme.typography.displaySmall,
            color = color
        )
    }
}

fun formatoTiempo(segundos: Int): String {
    val minutos = segundos / 60
    val segundosRestantes = segundos % 60
    return "%02d:%02d".format(minutos, segundosRestantes)
}