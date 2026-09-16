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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tallerescondidas.ui.components.BotonJuego
import com.example.tallerescondidas.ui.components.BotonJuegoSecundario
import com.example.tallerescondidas.ui.components.WormCanvas
import com.example.tallerescondidas.ui.theme.AmarilloEstrella
import com.example.tallerescondidas.ui.theme.Espacio
import com.example.tallerescondidas.ui.theme.PapelCrema
import com.example.tallerescondidas.ui.theme.TextoOscuro
import com.example.tallerescondidas.ui.theme.TextoSuave
import com.example.tallerescondidas.ui.theme.VerdeExito
import com.example.tallerescondidas.ui.theme.VerdeExitoClaro

@Composable
fun VictoryScreen(
    puntaje: Int,
    tiempoTotal: Int,
    precision: Int,
    onReiniciar: () -> Unit,
    onVolverInicio: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VerdeExitoClaro)
            .verticalScroll(rememberScrollState())
            .padding(Espacio.lg),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(Espacio.lg))

        Text(
            text = "Lo encontraste",
            style = MaterialTheme.typography.displayMedium,
            color = VerdeExito,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Espacio.sm))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
        ) {
            WormCanvas(temperatura = "CALIENTE", altura = 180.dp)
        }

        Spacer(modifier = Modifier.height(Espacio.lg))

        // Puntaje: el dato protagonista
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
                .background(PapelCrema)
                .padding(Espacio.lg),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = AmarilloEstrella,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.size(Espacio.sm))
                Text(
                    text = "$puntaje",
                    style = MaterialTheme.typography.displayLarge,
                    color = TextoOscuro
                )
            }

            Text(
                text = "puntos",
                style = MaterialTheme.typography.labelLarge,
                color = TextoSuave
            )

            Spacer(modifier = Modifier.height(Espacio.md))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DatoFinal("Tiempo", formatoTiempo(tiempoTotal))
                DatoFinal("Precision", "$precision por ciento")
            }
        }

        Spacer(modifier = Modifier.height(Espacio.lg))

        BotonJuego(
            texto = "Jugar de nuevo",
            icono = Icons.Default.Refresh,
            onClick = onReiniciar
        )

        Spacer(modifier = Modifier.height(Espacio.sm))

        BotonJuegoSecundario(
            texto = "Volver al inicio",
            icono = Icons.Default.Home,
            onClick = onVolverInicio
        )

        Spacer(modifier = Modifier.height(Espacio.lg))
    }
}

@Composable
private fun DatoFinal(
    etiqueta: String,
    valor: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = valor,
            style = MaterialTheme.typography.headlineMedium,
            color = TextoOscuro
        )
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.labelSmall,
            color = TextoSuave
        )
    }
}