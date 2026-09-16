package com.example.tallerescondidas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.tallerescondidas.ui.theme.Espacio
import com.example.tallerescondidas.ui.theme.Medida
import com.example.tallerescondidas.ui.theme.PapelCrema
import com.example.tallerescondidas.ui.theme.PapelSombra
import com.example.tallerescondidas.ui.theme.TextoOscuro
import com.example.tallerescondidas.ui.theme.VerdeBosque
import com.example.tallerescondidas.ui.theme.VerdeBosqueOscuro


@Composable
fun BotonJuego(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icono: ImageVector? = null,
    colorFondo: Color = VerdeBosque,
    colorTexto: Color = PapelCrema,
    colorRelieve: Color = VerdeBosqueOscuro
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(Medida.alturaBoton + Medida.relieveBoton)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Medida.alturaBoton)
                .offset(y = Medida.relieveBoton)
                .clip(MaterialTheme.shapes.small)
                .background(colorRelieve)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(Medida.alturaBoton)
                .clip(MaterialTheme.shapes.small)
                .background(colorFondo)
                .clickable { onClick() }
                .padding(horizontal = Espacio.md),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icono != null) {
                Icon(
                    imageVector = icono,
                    contentDescription = null,
                    tint = colorTexto,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(Espacio.sm))
            }

            Text(
                text = texto,
                color = colorTexto,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

/** Variante clara para acciones secundarias. */
@Composable
fun BotonJuegoSecundario(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icono: ImageVector? = null
) {
    BotonJuego(
        texto = texto,
        onClick = onClick,
        modifier = modifier,
        icono = icono,
        colorFondo = PapelCrema,
        colorTexto = TextoOscuro,
        colorRelieve = PapelSombra
    )
}