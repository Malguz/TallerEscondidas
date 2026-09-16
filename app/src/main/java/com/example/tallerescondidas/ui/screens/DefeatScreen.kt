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
import com.example.tallerescondidas.ui.components.GameButton
import com.example.tallerescondidas.ui.components.SecondaryGameButton
import com.example.tallerescondidas.ui.theme.HotRed
import com.example.tallerescondidas.ui.theme.LightHotRed
import com.example.tallerescondidas.ui.theme.Spacing
import com.example.tallerescondidas.ui.theme.CreamPaper
import com.example.tallerescondidas.ui.theme.DarkText
import com.example.tallerescondidas.ui.theme.SoftText

@Composable
fun DefeatScreen(
    finalDifference: Int,
    onRestart: () -> Unit,
    onBackToStart: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightHotRed)
            .verticalScroll(rememberScrollState())
            .padding(Spacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(Spacing.xl))

        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = CreamPaper,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
                .background(HotRed)
                .padding(Spacing.md)
        )

        Spacer(modifier = Modifier.height(Spacing.lg))

        Text(
            text = "Se acabo el tiempo",
            style = MaterialTheme.typography.displayMedium,
            color = HotRed,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Spacing.sm))

        Text(
            text = "El gusanito sigue escondido.",
            style = MaterialTheme.typography.bodyLarge,
            color = SoftText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Spacing.lg))

        // Instead of an empty encouragement message, tell how much was missing:
        // it's useful information for the next game.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
                .background(CreamPaper)
                .padding(Spacing.lg),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$finalDifference grados",
                style = MaterialTheme.typography.displaySmall,
                color = DarkText
            )
            Text(
                text = "te faltaban para encontrarlo",
                style = MaterialTheme.typography.labelLarge,
                color = SoftText
            )
        }

        Spacer(modifier = Modifier.height(Spacing.lg))

        GameButton(
            text = "Intentar de nuevo",
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