package com.example.tallerescondidas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.material3.Text

import androidx.compose.runtime.getValue
import com.example.tallerescondidas.sensors.OrientationProvider
class MainActivity : ComponentActivity() {

    private lateinit var orientationProvider: OrientationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orientationProvider = OrientationProvider(this)

        setContent {
            val azimuth by orientationProvider.azimuth
            Text(text = "Azimut: ${azimuth.toInt()}°")
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