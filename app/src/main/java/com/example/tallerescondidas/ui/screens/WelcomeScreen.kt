package com.example.tallerescondidas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tallerescondidas.ui.components.BotonJuego
import com.example.tallerescondidas.ui.components.WormCanvas
import com.example.tallerescondidas.ui.theme.CalienteRojo
import com.example.tallerescondidas.ui.theme.Espacio
import com.example.tallerescondidas.ui.theme.FrioAzul
import com.example.tallerescondidas.ui.theme.MaderaMarron
import com.example.tallerescondidas.ui.theme.MaderaOscura
import com.example.tallerescondidas.ui.theme.PapelCrema
import com.example.tallerescondidas.ui.theme.TextoOscuro
import com.example.tallerescondidas.ui.theme.TextoSuave
import com.example.tallerescondidas.ui.theme.TibioNaranja
import com.example.tallerescondidas.ui.theme.VerdeCesped

@Composable
fun WelcomeScreen(
    onStartGame: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VerdeCesped)
            .verticalScroll(rememberScrollState())
            .padding(Espacio.lg),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(Espacio.lg))

        // Cartel de madera: el unico elemento con textura del juego
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
                .background(MaderaMarron)
                .border(4.dp, MaderaOscura, MaterialTheme.shapes.large)
                .padding(vertical = Espacio.lg, horizontal = Espacio.md),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "ESCONDIDAS",
                    style = MaterialTheme.typography.displayLarge,
                    color = PapelCrema
                )
                Text(
                    text = "Encuentra al gusanito",
                    style = MaterialTheme.typography.bodyLarge,
                    color = PapelCrema.copy(alpha = 0.85f)
                )
            }
        }

        Spacer(modifier = Modifier.height(Espacio.lg))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
        ) {
            WormCanvas(temperatura = "CALIENTE", altura = 170.dp)
        }

        Spacer(modifier = Modifier.height(Espacio.lg))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
                .background(PapelCrema)
                .padding(Espacio.lg)
        ) {
            Text(
                text = "Como jugar",
                style = MaterialTheme.typography.titleLarge,
                color = TextoOscuro
            )

            Spacer(modifier = Modifier.height(Espacio.sm))

            Text(
                text = "Gira tu telefono para buscar la direccion donde se " +
                        "escondio el gusanito. La barra te dice si vas bien.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextoSuave
            )

            Spacer(modifier = Modifier.height(Espacio.md))

            Pista(FrioAzul, "Muy frio", "Estas apuntando al lado contrario")
            Pista(TibioNaranja, "Tibio", "Vas por buen camino, sigue girando")
            Pista(CalienteRojo, "Caliente", "Esta casi justo delante de ti")
        }

        Spacer(modifier = Modifier.height(Espacio.lg))

        BotonJuego(
            texto = "Nueva partida",
            icono = Icons.Default.PlayArrow,
            onClick = onStartGame
        )

        Spacer(modifier = Modifier.height(Espacio.lg))
    }
}

@Composable
private fun Pista(
    color: Color,
    titulo: String,
    detalle: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Espacio.xs),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .clip(CircleShape)
                .background(color)
        )

        Spacer(modifier = Modifier.width(Espacio.sm))

        Column {
            Text(
                text = titulo,
                style = MaterialTheme.typography.labelLarge,
                color = color
            )
            Text(
                text = detalle,
                style = MaterialTheme.typography.bodyMedium,
                color = TextoSuave
            )
        }
    }
}