package com.example.tallerescondidas.logic

object GameLogic{
    private const val  BASE_SCORE = 1000
    private const val PENALTY_PER_SECOND = 10
    private const val  PENALTY_PER_GRADE = 5
    private const val PRECISION_BONUS = 150
    private const val  BONUS_MARGIN = 5.0
/**
  *  @param: timeUsedSeconds
 *   @param: angularDifferenceDegrees
*/

fun timeOut (timeRemainingSec: Int): Boolean{
    return timeRemainingSec <= 0
}
fun targetFound(angularDifferenceDegrees: Double): Boolean {
        return angularDifferenceDegrees <= 5.0
    }

/*     Calculate the angular difference between two angles of 0-360.
* example, the angles 340 and 10 are 20 degrees apart.
 */

fun calculateAngleDifference(currentAngle: Float, angleTarget: Float): Double {
    val  diference = Math.abs(currentAngle - angleTarget).toDouble()
    return if (diference > 180) 360 - diference else diference
}



    /* Generates a random target direction */
    fun generateTargetDirection(): Float {
        return (0..359).random().toFloat()
    }
fun calculateScore(timeUsedSeconds: Int, angularDifferenceDegrees: Double ): Int{
        var score = BASE_SCORE
        score -= timeUsedSeconds * PENALTY_PER_SECOND
        score -= (angularDifferenceDegrees * PENALTY_PER_GRADE).toInt()

      if (angularDifferenceDegrees <= BONUS_MARGIN){
            score += PRECISION_BONUS
      }
         return score.coerceAtLeast(0)
    }

    fun calculatePrecision(angularDifferenceDegrees: Double, maxMargin: Double = 90.0): Int {
        val precision = 100 - (angularDifferenceDegrees / maxMargin * 100)
        return precision.coerceIn(0.0, 100.0).toInt()
    }

    fun getTemperatureState(angularDifferenceDegrees: Double): TemperatureState {
        return when {
            angularDifferenceDegrees <= 15.0 -> TemperatureState.HOT
            angularDifferenceDegrees <= 45.0 -> TemperatureState.WARM
            else -> TemperatureState.COLD
        }
    }
}

enum class TemperatureState { COLD, WARM, HOT }