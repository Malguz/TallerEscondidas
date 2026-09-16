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
import com.example.tallerescondidas.ui.theme.TallerEscondidasTheme
import com.example.tallerescondidas.ui.theme.VerdeCesped
import kotlinx.coroutines.delay

private const val DURACION_PARTIDA = 25

class MainActivity : ComponentActivity() {

    private lateinit var orientationProvider: OrientationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        orientationProvider = OrientationProvider(this)

        setContent {
            TallerEscondidasTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(VerdeCesped)
                ) {
                    PantallaPrincipal(orientationProvider)
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
fun PantallaPrincipal(
    orientationProvider: OrientationProvider
) {
    var estadoActual by remember { mutableStateOf(EstadoJuego.INICIO) }
    var tiempoRestante by remember { mutableIntStateOf(DURACION_PARTIDA) }
    var tiempoUsado by remember { mutableIntStateOf(0) }

    var direccionObjetivo by remember {
        mutableStateOf(GameLogic.generarDireccionObjetivo())
    }

    // Resultados congelados en el momento de terminar la partida
    var puntajeFinal by remember { mutableIntStateOf(0) }
    var precisionFinal by remember { mutableIntStateOf(0) }
    var tiempoFinal by remember { mutableIntStateOf(0) }
    var diferenciaFinal by remember { mutableIntStateOf(0) }

    val azimuth by orientationProvider.azimuth

    val diferencia = GameLogic.calcularDiferenciaAngular(azimuth, direccionObjetivo)
    val temperatura = GameLogic.obtenerEstadoTemperatura(diferencia)

    // El puntaje se recalcula solo: no hace falta guardarlo en un estado
    // aparte, y asi nunca queda desincronizado con la diferencia actual.
    val puntaje = GameLogic.calcularPuntaje(tiempoUsado, diferencia)

    fun nuevaPartida() {
        tiempoRestante = DURACION_PARTIDA
        tiempoUsado = 0
        direccionObjetivo = GameLogic.generarDireccionObjetivo()
        estadoActual = EstadoJuego.JUGANDO
    }

    // Victoria
    LaunchedEffect(diferencia, estadoActual) {
        if (estadoActual == EstadoJuego.JUGANDO && GameLogic.objetivoEncontrado(diferencia)) {
            puntajeFinal = puntaje
            precisionFinal = GameLogic.calcularPrecision(diferencia)
            tiempoFinal = tiempoUsado
            estadoActual = EstadoJuego.VICTORIA
        }
    }

    // Cronometro
    LaunchedEffect(estadoActual) {
        if (estadoActual == EstadoJuego.JUGANDO) {
            while (tiempoRestante > 0 && estadoActual == EstadoJuego.JUGANDO) {
                delay(1000)
                tiempoRestante--
                tiempoUsado++
            }

            if (estadoActual == EstadoJuego.JUGANDO && GameLogic.tiempoAgotado(tiempoRestante)) {
                diferenciaFinal = diferencia.toInt()
                estadoActual = EstadoJuego.DERROTA
            }
        }
    }

    when (estadoActual) {

        EstadoJuego.INICIO -> WelcomeScreen(
            onStartGame = { nuevaPartida() }
        )

        EstadoJuego.JUGANDO -> GameScreen(
            tiempoRestante = tiempoRestante,
            puntaje = puntaje,
            temperatura = temperatura.name,
            diferencia = diferencia,
            azimuth = azimuth,
            direccionObjetivo = direccionObjetivo,
            onReiniciarClick = { nuevaPartida() }
        )

        EstadoJuego.VICTORIA -> VictoryScreen(
            puntaje = puntajeFinal,
            tiempoTotal = tiempoFinal,
            precision = precisionFinal,
            onReiniciar = { nuevaPartida() },
            onVolverInicio = { estadoActual = EstadoJuego.INICIO }
        )

        EstadoJuego.DERROTA -> DefeatScreen(
            diferenciaFinal = diferenciaFinal,
            onReiniciar = { nuevaPartida() },
            onVolverInicio = { estadoActual = EstadoJuego.INICIO }
        )
    }
}