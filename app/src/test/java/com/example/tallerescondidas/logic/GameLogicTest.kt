package com.example.tallerescondidas.logic

import org.junit.Assert.*
import org.junit.Test

class GameLogicTest {

   @Test
   fun `tiempo agotado cuando llega a cero`(){
       assertEquals(true, GameLogic.tiempoAgotado(0))
       assertEquals(false, GameLogic.tiempoAgotado(5))
   }

    @Test
    fun `puntaje maximo cuando el tiempo y la diferencia angular son cero`() {
        val puntaje = GameLogic.calcularPuntaje(tiempoUsadoSegundos = 0, diferenciaAngularGrados = 0.0)
        assertEquals(1150, puntaje)
    }

    @Test
    fun `puntaje nunca es negativo`() {
        val puntaje = GameLogic.calcularPuntaje(tiempoUsadoSegundos = 500, diferenciaAngularGrados = 180.0)
        assertEquals(0, puntaje)
    }
    @Test
    fun `diferencia angular considera el cruce por 0 grados`() {
        val diferencia = GameLogic.calcularDiferenciaAngular(anguloActual = 350f, anguloObjetivo = 10f)
        assertEquals(20.0, diferencia, 0.01)
    }
}