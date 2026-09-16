package com.example.tallerescondidas

/**
 * EstadoTemperatura vive unicamente en GameLogic.kt (paquete .logic).
 * Antes existia una copia aqui: compilaba porque estaban en paquetes
 * distintos, pero era una trampa esperando a confundir un import.
 * Se elimino esa copia duplicada.
 */
enum class EstadoJuego {
    INICIO,
    JUGANDO,
    VICTORIA,
    DERROTA
}