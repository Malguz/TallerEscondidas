package com.example.tallerescondidas.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.State

class OrientationProvider (context: Context) : SensorEventListener {

    private val sensorManager =  context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer =  sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    private val gravity = FloatArray(3)
    private val geomagnetic = FloatArray(3)
    private val rotationMatrix = FloatArray(9)
    private val orientationValues = FloatArray (3)


    private val _azimuth = mutableFloatStateOf(0f)
    val azimuth: State<Float> get() = _azimuth

    companion object{
        private  const val FACTOR_SUAVIZADO = 0.15f
    }
    fun start() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME)
    }

    fun stop(){
        sensorManager.unregisterListener(this)
    }

    fun sensoresDisponibles(): Boolean{
        return accelerometer != null && magnetometer != null
    }

    private fun suavizarAngulo(anguloActual: Float, anguloNuevo: Float): Float{
        var diferencia = anguloNuevo - anguloActual
        if (diferencia > 180) diferencia -=360
        if (diferencia < -180) diferencia +=360
        var resultado = anguloActual + FACTOR_SUAVIZADO * diferencia
        if (resultado< 0) resultado += 360
        if (resultado >= 360) resultado -= 360
        return resultado
    }

    override fun onSensorChanged(event: SensorEvent) {

        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> System.arraycopy(event.values, 0, gravity, 0, gravity.size)
            Sensor.TYPE_MAGNETIC_FIELD -> System.arraycopy(event.values, 0, geomagnetic, 0, geomagnetic.size)
        }

        val success = SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geomagnetic)
        if (success) {
            SensorManager.getOrientation(rotationMatrix, orientationValues)
            val azimuthRadians = orientationValues[0]
            var nuevoAzimuth = Math.toDegrees(azimuthRadians.toDouble()).toFloat()
            if (nuevoAzimuth < 0) nuevoAzimuth += 360f

            _azimuth.floatValue = suavizarAngulo(_azimuth.floatValue, nuevoAzimuth)
          }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No lo necesitamos para este juego, pero el método es obligatorio.
    }
}