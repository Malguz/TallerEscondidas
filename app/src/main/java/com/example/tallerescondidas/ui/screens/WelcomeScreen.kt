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
import com.example.tallerescondidas.ui.components.GameButton
import com.example.tallerescondidas.ui.components.WormCanvas
import com.example.tallerescondidas.ui.theme.HotRed
import com.example.tallerescondidas.ui.theme.Spacing
import com.example.tallerescondidas.ui.theme.ColdBlue
import com.example.tallerescondidas.ui.theme.BrownWood
import com.example.tallerescondidas.ui.theme.DarkWood
import com.example.tallerescondidas.ui.theme.CreamPaper
import com.example.tallerescondidas.ui.theme.DarkText
import com.example.tallerescondidas.ui.theme.SoftText
import com.example.tallerescondidas.ui.theme.WarmOrange
import com.example.tallerescondidas.ui.theme.GrassGreen

@Composable
fun WelcomeScreen(
    onStartGame: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrassGreen)
            .verticalScroll(rememberScrollState())
            .padding(Spacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(Spacing.lg))

        // Wooden sign: the only textured element in the game
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
                .background(BrownWood)
                .border(4.dp, DarkWood, MaterialTheme.shapes.large)
                .padding(vertical = Spacing.lg, horizontal = Spacing.md),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "ESCONDIDAS",
                    style = MaterialTheme.typography.displayLarge,
                    color = CreamPaper
                )
                Text(
                    text = "Encuentra al gusanito",
                    style = MaterialTheme.typography.bodyLarge,
                    color = CreamPaper.copy(alpha = 0.85f)
                )
            }
        }

        Spacer(modifier = Modifier.height(Spacing.lg))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
        ) {
            WormCanvas(temperature = "HOT", height = 170.dp)
        }

        Spacer(modifier = Modifier.height(Spacing.lg))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
                .background(CreamPaper)
                .padding(Spacing.lg)
        ) {
            Text(
                text = "Como jugar",
                style = MaterialTheme.typography.titleLarge,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(Spacing.sm))

            Text(
                text = "Gira tu telefono para buscar la direccion donde se " +
                        "escondio el gusanito. La barra te dice si vas bien.",
                style = MaterialTheme.typography.bodyMedium,
                color = SoftText
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            Hint(ColdBlue, "Muy frio", "Estas apuntando al lado contrario")
            Hint(WarmOrange, "Tibio", "Vas por buen camino, sigue girando")
            Hint(HotRed, "Caliente", "Esta casi justo delante de ti")
        }

        Spacer(modifier = Modifier.height(Spacing.lg))

        GameButton(
            text = "Nueva partida",
            icon = Icons.Default.PlayArrow,
            onClick = onStartGame
        )

        Spacer(modifier = Modifier.height(Spacing.lg))
    }
}

@Composable
private fun Hint(
    color: Color,
    title: String,
    detail: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.xs),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .clip(CircleShape)
                .background(color)
        )

        Spacer(modifier = Modifier.width(Spacing.sm))

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = color
            )
            Text(
                text = detail,
                style = MaterialTheme.typography.bodyMedium,
                color = SoftText
            )
        }
    }
}