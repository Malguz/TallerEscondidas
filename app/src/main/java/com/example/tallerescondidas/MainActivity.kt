package com.example.tallerescondidas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.tallerescondidas.logic.GameLogic
import com.example.tallerescondidas.sensors.OrientationProvider
import com.example.tallerescondidas.ui.screens.DefeatScreen
import com.example.tallerescondidas.ui.screens.GameScreen
import com.example.tallerescondidas.ui.screens.VictoryScreen
import com.example.tallerescondidas.ui.screens.WelcomeScreen
import com.example.tallerescondidas.ui.theme.HiddenWorkshopTheme
import com.example.tallerescondidas.ui.theme.GrassGreen
import kotlinx.coroutines.delay

private const val GAME_DURATION = 25

class MainActivity : ComponentActivity() {

    private lateinit var orientationProvider: OrientationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        orientationProvider = OrientationProvider(this)

        setContent {
            HiddenWorkshopTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(GrassGreen)
                ) {
                    MainScreen(orientationProvider)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        orientationProvider.start()
    }

    override fun onPause() {
        super.onPause()
        orientationProvider.stop()
    }
}

@Composable
fun MainScreen(
    orientationProvider: OrientationProvider
) {
    var currentState by remember { mutableStateOf(EstadoJuego.BEGINNING) }
    var timeRemaining by remember { mutableIntStateOf(GAME_DURATION) }
    var timeUsed by remember { mutableIntStateOf(0) }

    var targetDirection by remember {
        mutableStateOf(GameLogic.generateTargetDirection())
    }

    // Results frozen at the moment of game over
    var finalScore by remember { mutableIntStateOf(0) }
    var finalPrecision by remember { mutableIntStateOf(0) }
    var finalTime by remember { mutableIntStateOf(0) }
    var finalDifference by remember { mutableIntStateOf(0) }

    val azimuth by orientationProvider.azimuth

    val difference = GameLogic.calculateAngleDifference(azimuth, targetDirection)
    val temperature = GameLogic.getTemperatureState(difference)

    // Score is recalculated automatically: no need to save it in a separate state,
    // so it never gets out of sync with the current difference.
    val score = GameLogic.calculateScore(timeUsed, difference)

    fun newGame() {
        timeRemaining = GAME_DURATION
        timeUsed = 0
        targetDirection = GameLogic.generateTargetDirection()
        currentState = EstadoJuego.PLAYING
    }

    // VICTORY
    LaunchedEffect(difference, currentState) {
        if (currentState == EstadoJuego.PLAYING && GameLogic.targetFound(difference)) {
            finalScore = score
            finalPrecision = GameLogic.calculatePrecision(difference)
            finalTime = timeUsed
            currentState = EstadoJuego.VICTORY
        }
    }

    // Timer
    LaunchedEffect(currentState) {
        if (currentState == EstadoJuego.PLAYING) {
            while (timeRemaining > 0 && currentState == EstadoJuego.PLAYING) {
                delay(1000)
                timeRemaining--
                timeUsed++
            }

            if (currentState == EstadoJuego.PLAYING && GameLogic.timeOut(timeRemaining)) {
                finalDifference = difference.toInt()
                currentState = EstadoJuego.DEFEAT
            }
        }
    }

    when (currentState) {

        EstadoJuego.BEGINNING -> WelcomeScreen(
            onStartGame = { newGame() }
        )

        EstadoJuego.PLAYING -> GameScreen(
            timeRemaining = timeRemaining,
            score = score,
            temperature = temperature.name,
            difference = difference,
            azimuth = azimuth,
            targetDirection = targetDirection,
            onRestartClick = { newGame() }
        )

        EstadoJuego.VICTORY -> VictoryScreen(
            score = finalScore,
            totalTime = finalTime,
            precision = finalPrecision,
            onRestart = { newGame() },
            onBackToStart = { currentState = EstadoJuego.BEGINNING }
        )

        EstadoJuego.DEFEAT -> DefeatScreen(
            finalDifference = finalDifference,
            onRestart = { newGame() },
            onBackToStart = { currentState = EstadoJuego.BEGINNING }
        )
    }
}