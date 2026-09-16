package com.example.tallerescondidas.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


private val TitleFont = FontFamily.SansSerif
private val BodyFont = FontFamily.Serif

val Typography = Typography(

    // Main sign on the start screen
    displayLarge = TextStyle(
        fontFamily = TitleFont,
        fontWeight = FontWeight.Black,
        fontSize = 44.sp,
        lineHeight = 46.sp,
        letterSpacing = (-1).sp
    ),

    // Titles for VICTORY / DEFEAT
    displayMedium = TextStyle(
        fontFamily = TitleFont,
        fontWeight = FontWeight.Black,
        fontSize = 30.sp,
        lineHeight = 34.sp,
        letterSpacing = (-0.5).sp
    ),

    // Large timer and score
    displaySmall = TextStyle(
        fontFamily = TitleFont,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        lineHeight = 36.sp,
        letterSpacing = 1.sp
    ),

    headlineMedium = TextStyle(
        fontFamily = TitleFont,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),

    titleLarge = TextStyle(
        fontFamily = TitleFont,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),

    // Text inside buttons
    titleMedium = TextStyle(
        fontFamily = TitleFont,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.4.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = BodyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.2.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = BodyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp
    ),

    labelLarge = TextStyle(
        fontFamily = TitleFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.6.sp
    ),

    labelSmall = TextStyle(
        fontFamily = TitleFont,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp
    )
)