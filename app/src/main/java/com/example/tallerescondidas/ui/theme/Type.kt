package com.example.tallerescondidas.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


private val Titulo = FontFamily.SansSerif
private val Lectura = FontFamily.Serif

val Typography = Typography(

    // Cartel principal de la pantalla de inicio
    displayLarge = TextStyle(
        fontFamily = Titulo,
        fontWeight = FontWeight.Black,
        fontSize = 44.sp,
        lineHeight = 46.sp,
        letterSpacing = (-1).sp
    ),

    // Titulos de victoria / derrota
    displayMedium = TextStyle(
        fontFamily = Titulo,
        fontWeight = FontWeight.Black,
        fontSize = 30.sp,
        lineHeight = 34.sp,
        letterSpacing = (-0.5).sp
    ),

    // Cronometro y puntaje grande
    displaySmall = TextStyle(
        fontFamily = Titulo,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        lineHeight = 36.sp,
        letterSpacing = 1.sp
    ),

    headlineMedium = TextStyle(
        fontFamily = Titulo,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),

    titleLarge = TextStyle(
        fontFamily = Titulo,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),

    // Texto dentro de botones
    titleMedium = TextStyle(
        fontFamily = Titulo,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.4.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = Lectura,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.2.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = Lectura,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp
    ),


    labelLarge = TextStyle(
        fontFamily = Titulo,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.6.sp
    ),

    labelSmall = TextStyle(
        fontFamily = Titulo,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp
    )
)