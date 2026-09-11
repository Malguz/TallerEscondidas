package com.example.tallerescondidas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import com.example.tallerescondidas.sensors.OrientationProvider
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.tallerescondidas.logic.GameLogic
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {

    private lateinit var orientationProvider: OrientationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orientationProvider = OrientationProvider(this)


        setContent {
            val azimuth by orientationProvider.azimuth
            var direccionObjetivo by remember { mutableFloatStateOf(GameLogic.generarDireccionObjetivo()) }
           var tiempoRestante by remember { mutableStateOf(60) }  // se le da al jugador 60 seg, pero se puede ajustar
            var juegoTerminado by remember { mutableStateOf(false)}

            val diferencia = GameLogic.calcularDiferenciaAngular(azimuth, direccionObjetivo)
            val estado = GameLogic.obtenerEstadoTemperatura(diferencia)

            LaunchedEffect(juegoTerminado) {
                while (tiempoRestante > 0 && !juegoTerminado){
                    delay(1000)
                    tiempoRestante -= 1
                    if (GameLogic.tiempoAgotado(tiempoRestante)){
                        juegoTerminado = true
                    }
                }
            }

            Column {
                Text(text = "Azimut actual: ${azimuth.toInt()}°")
                Text(text = "Direccion objetivo: ${direccionObjetivo.toInt()}°")
                Text(text = "Diferencia: ${diferencia.toInt()}°")
                Text(text = "Estado: $estado")
                if(juegoTerminado){
                    Text(text ="!Se acabo el tiempo! :'( " +
                            "No encontraste al personaje.")
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