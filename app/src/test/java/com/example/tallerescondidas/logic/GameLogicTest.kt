package com.example.tallerescondidas.logic

import org.junit.Assert.*
import org.junit.Test

class GameLogicTest {

   @Test
   fun `time out when reaches zero`(){
       assertEquals(true, GameLogic.timeOut(0))
       assertEquals(false, GameLogic.timeOut(5))
   }

    @Test
    fun `max score when time and angular difference are zero`() {
        val score = GameLogic.calculateScore(timeUsedSeconds = 0, angularDifferenceDegrees = 0.0)
        assertEquals(1150, score)
    }

    @Test
    fun `score is never negative`() {
        val score = GameLogic.calculateScore(timeUsedSeconds = 500, angularDifferenceDegrees = 180.0)
        assertEquals(0, score)
    }

    @Test
    fun `angular difference considers crossing 0 degrees`() {
        val difference = GameLogic.calculateAngleDifference(currentAngle = 350f, angleTarget = 10f)
        assertEquals(20.0, difference, 0.01)
    }
    @Test
    fun `target found within margin`() {
        assertEquals(true, GameLogic.targetFound(3.0))
        assertEquals(false, GameLogic.targetFound(15.0))
    }
}