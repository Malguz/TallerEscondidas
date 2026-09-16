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
import com.example.tallerescondidas.ui.theme.Spacing
import com.example.tallerescondidas.ui.theme.Measurement
import com.example.tallerescondidas.ui.theme.CreamPaper
import com.example.tallerescondidas.ui.theme.ShadowPaper
import com.example.tallerescondidas.ui.theme.DarkText
import com.example.tallerescondidas.ui.theme.ForestGreen
import com.example.tallerescondidas.ui.theme.DarkForestGreen

@Composable
fun GameButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    backgroundColor: Color = ForestGreen,
    textColor: Color = CreamPaper,
    reliefColor: Color = DarkForestGreen
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(Measurement.buttonHeight + Measurement.buttonRelief)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Measurement.buttonHeight)
                .offset(y = Measurement.buttonRelief)
                .clip(MaterialTheme.shapes.small)
                .background(reliefColor)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(Measurement.buttonHeight)
                .clip(MaterialTheme.shapes.small)
                .background(backgroundColor)
                .clickable { onClick() }
                .padding(horizontal = Spacing.md),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = textColor,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(Spacing.sm))
            }

            Text(
                text = text,
                color = textColor,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

/** Light variant for secondary actions. */
@Composable
fun SecondaryGameButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {
    GameButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        icon = icon,
        backgroundColor = CreamPaper,
        textColor = DarkText,
        reliefColor = ShadowPaper
    )
}