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
import com.example.tallerescondidas.ui.components.GameButton
import com.example.tallerescondidas.ui.components.SecondaryGameButton
import com.example.tallerescondidas.ui.components.WormCanvas
import com.example.tallerescondidas.ui.theme.StarYellow
import com.example.tallerescondidas.ui.theme.Spacing
import com.example.tallerescondidas.ui.theme.CreamPaper
import com.example.tallerescondidas.ui.theme.DarkText
import com.example.tallerescondidas.ui.theme.SoftText
import com.example.tallerescondidas.ui.theme.SuccessGreen
import com.example.tallerescondidas.ui.theme.LightSuccessGreen

@Composable
fun VictoryScreen(
    score: Int,
    totalTime: Int,
    precision: Int,
    onRestart: () -> Unit,
    onBackToStart: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightSuccessGreen)
            .verticalScroll(rememberScrollState())
            .padding(Spacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(Spacing.lg))

        Text(
            text = "Lo encontraste",
            style = MaterialTheme.typography.displayMedium,
            color = SuccessGreen,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Spacing.sm))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
        ) {
            WormCanvas(temperature = "HOT", height = 180.dp)
        }

        Spacer(modifier = Modifier.height(Spacing.lg))

        // Score: the main data
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
                .background(CreamPaper)
                .padding(Spacing.lg),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = StarYellow,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.size(Spacing.sm))
                Text(
                    text = "$score",
                    style = MaterialTheme.typography.displayLarge,
                    color = DarkText
                )
            }

            Text(
                text = "puntos",
                style = MaterialTheme.typography.labelLarge,
                color = SoftText
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FinalStat("Tiempo", formatTime(totalTime))
                FinalStat("Precision", "$precision por ciento")
            }
        }

        Spacer(modifier = Modifier.height(Spacing.lg))

        GameButton(
            text = "Jugar de nuevo",
            icon = Icons.Default.Refresh,
            onClick = onRestart
        )

        Spacer(modifier = Modifier.height(Spacing.sm))

        SecondaryGameButton(
            text = "Volver al inicio",
            icon = Icons.Default.Home,
            onClick = onBackToStart
        )

        Spacer(modifier = Modifier.height(Spacing.lg))
    }
}

@Composable
private fun FinalStat(
    label: String,
    value: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            color = DarkText
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = SoftText
        )
    }
}