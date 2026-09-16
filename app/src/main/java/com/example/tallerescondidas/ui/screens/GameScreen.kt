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
import com.example.tallerescondidas.ui.components.SecondaryGameButton
import com.example.tallerescondidas.ui.components.DirectionIndicator
import com.example.tallerescondidas.ui.components.TemperatureIndicator
import com.example.tallerescondidas.ui.components.WormCanvas
import com.example.tallerescondidas.ui.components.getTemperatureColor
import com.example.tallerescondidas.ui.theme.StarYellow
import com.example.tallerescondidas.ui.theme.HotRed
import com.example.tallerescondidas.ui.theme.Spacing
import com.example.tallerescondidas.ui.theme.CreamPaper
import com.example.tallerescondidas.ui.theme.DarkText
import com.example.tallerescondidas.ui.theme.SoftText
import com.example.tallerescondidas.ui.theme.ForestGreen
import com.example.tallerescondidas.ui.theme.GrassGreen

@Composable
fun GameScreen(
    timeRemaining: Int,
    score: Int,
    temperature: String,
    difference: Double,
    azimuth: Float,
    targetDirection: Float,
    onRestartClick: () -> Unit
) {
    val currentColor = getTemperatureColor(temperature)
    val isTimeRunningOut = timeRemaining <= 10

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrassGreen)
            .verticalScroll(rememberScrollState())
            .padding(Spacing.md),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Scoreboard: time on the left, points on the right
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            Scoreboard(
                label = "Tiempo",
                value = formatTime(timeRemaining),
                color = if (isTimeRunningOut) HotRed else DarkText,
                modifier = Modifier.weight(1f)
            )

            Scoreboard(
                label = "Puntos",
                value = score.toString(),
                color = DarkText,
                icon = true,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(Spacing.sm))

        // Permanent instruction
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.small)
                .background(CreamPaper)
                .padding(Spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = ForestGreen,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(Spacing.sm))
            Text(
                text = "Gira el telefono hasta que la barra llegue a caliente",
                style = MaterialTheme.typography.bodyMedium,
                color = SoftText
            )
        }

        Spacer(modifier = Modifier.height(Spacing.md))

        // Scenario with the compass on top
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large),
            contentAlignment = Alignment.Center
        ) {
            WormCanvas(
                temperature = temperature,
                height = 300.dp
            )

            DirectionIndicator(
                direction = targetDirection - azimuth,
                guideColor = currentColor
            )
        }

        Spacer(modifier = Modifier.height(Spacing.md))

        TemperatureIndicator(
            state = temperature,
            difference = difference
        )

        Spacer(modifier = Modifier.height(Spacing.sm))

        Text(
            text = "Te faltan ${difference.toInt()} grados",
            style = MaterialTheme.typography.labelSmall,
            color = SoftText
        )

        Spacer(modifier = Modifier.height(Spacing.md))

        SecondaryGameButton(
            text = "Reiniciar partida",
            icon = Icons.Default.Refresh,
            onClick = onRestartClick
        )

        Spacer(modifier = Modifier.height(Spacing.md))
    }
}

@Composable
private fun Scoreboard(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier,
    icon: Boolean = false
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .background(CreamPaper)
            .padding(vertical = Spacing.sm, horizontal = Spacing.md)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = StarYellow,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(Spacing.xs))
            }
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = SoftText
            )
        }

        Text(
            text = value,
            style = MaterialTheme.typography.displaySmall,
            color = color
        )
    }
}

fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
}