package com.example.tallerescondidas.logic

object GameLogic{
    private const val  PUNTAJE_BASE = 1000
    private const val PENALIZACION_POR_SEGUNDO = 10
    private const val  PENALIZACION_POR_GRADO = 5
    private const val BONIFICACION_PRECISION = 150
    private const val  MARGEN_BONIFICACION = 5.0
/**
  *  @param: tiempoUsadoSegundos
 *   @param: diferenciaAngularGrados
*/
    fun calcularPuntaje(tiempoUsadoSegundos: Int, diferenciaAngularGrados: Double ): Int{
        var puntaje = PUNTAJE_BASE
        puntaje -= tiempoUsadoSegundos * PENALIZACION_POR_SEGUNDO
        puntaje -= (diferenciaAngularGrados * PENALIZACION_POR_GRADO).toInt()

      if (diferenciaAngularGrados <= MARGEN_BONIFICACION){
            puntaje += BONIFICACION_PRECISION
      }
         return puntaje.coerceAtLeast(0)
    }

    fun calcularPrecision(diferenciaAngularGrados: Double, margenMaximo: Double = 90.0 ): Int {
        val precision = 100 - ( diferenciaAngularGrados / margenMaximo * 100)
        return precision.coerceIn(0.0, 100.0).toInt()
    }

    fun obtenerEstadoTemperatura(diferenciaAngularGrados: Double ): EstadoTemperatura {
            return when {
                diferenciaAngularGrados <= 15.0 -> EstadoTemperatura.CALIENTE
                diferenciaAngularGrados <= 45.0 -> EstadoTemperatura.TIBIO
                else -> EstadoTemperatura.FRIO
            }
    }
}
enum class EstadoTemperatura{ FRIO, TIBIO, CALIENTE }