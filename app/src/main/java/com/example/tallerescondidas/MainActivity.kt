package com.example.tallerescondidas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import com.example.tallerescondidas.sensors.OrientationProvider
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import com.example.tallerescondidas.logic.GameLogic
class MainActivity : ComponentActivity() {

    private lateinit var orientationProvider: OrientationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orientationProvider = OrientationProvider(this)

        setContent {
            val azimuth by orientationProvider.azimuth
            var direccionObjetivo by remember { mutableFloatStateOf(GameLogic.generarDireccionObjetivo()) }

            val diferencia = GameLogic.calcularDiferenciaAngular(azimuth, direccionObjetivo)
            val estado = GameLogic.obtenerEstadoTemperatura(diferencia)

            Column {
                Text(text = "Azimut actual: ${azimuth.toInt()}°")
                Text(text = "Direccion objetivo: ${direccionObjetivo.toInt()}°")
                Text(text = "Diferencia: ${diferencia.toInt()}°")
                Text(text = "Estado: $estado")
            }
    }}

    override fun onResume() {
        super.onResume()
        orientationProvider.start()
    }

    override fun onPause() {
        super.onPause()
        orientationProvider.stop()
    }
}