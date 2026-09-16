package com.example.tallerescondidas

/**
 * TemperatureState lives only in GameLogic.kt (.logic package).
 * Previously there was a duplicate here: it compiled because they were in
 * different packages, but it was a trap waiting to confuse an import.
 * That duplicate copy was removed.
 */
enum class EstadoJuego {
    BEGINNING,
    PLAYING,
    VICTORY,
    DEFEAT
}