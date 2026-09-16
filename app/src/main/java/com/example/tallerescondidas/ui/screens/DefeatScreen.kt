package com.example.tallerescondidas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
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
import com.example.tallerescondidas.ui.theme.CalienteRojo
import com.example.tallerescondidas.ui.theme.CalienteRojoClaro
import com.example.tallerescondidas.ui.theme.Espacio
import com.example.tallerescondidas.ui.theme.PapelCrema
import com.example.tallerescondidas.ui.theme.TextoOscuro
import com.example.tallerescondidas.ui.theme.TextoSuave

@Composable
fun DefeatScreen(
    diferenciaFinal: Int,
    onReiniciar: () -> Unit,
    onVolverInicio: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CalienteRojoClaro)
            .verticalScroll(rememberScrollState())
            .padding(Espacio.lg),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(Espacio.xl))

        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = PapelCrema,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
                .background(CalienteRojo)
                .padding(Espacio.md)
        )

        Spacer(modifier = Modifier.height(Espacio.lg))

        Text(
            text = "Se acabo el tiempo",
            style = MaterialTheme.typography.displayMedium,
            color = CalienteRojo,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Espacio.sm))

        Text(
            text = "El gusanito sigue escondido.",
            style = MaterialTheme.typography.bodyLarge,
            color = TextoSuave,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Espacio.lg))

        // En vez de un mensaje de animo vacio, se dice cuanto falto:
        // es informacion util para la siguiente partida.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
                .background(PapelCrema)
                .padding(Espacio.lg),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$diferenciaFinal grados",
                style = MaterialTheme.typography.displaySmall,
                color = TextoOscuro
            )
            Text(
                text = "te faltaban para encontrarlo",
                style = MaterialTheme.typography.labelLarge,
                color = TextoSuave
            )
        }

        Spacer(modifier = Modifier.height(Espacio.lg))

        BotonJuego(
            texto = "Intentar de nuevo",
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