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

    companion object {
        private const val SMOOTHING_FACTOR = 0.15f
    }
    fun start() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME)
    }

    fun stop(){
        sensorManager.unregisterListener(this)
    }



    private fun smoothAngle(currentAngle: Float, newAngle: Float): Float {
        var difference = newAngle - currentAngle
        if (difference > 180) difference -= 360
        if (difference < -180) difference += 360
        var result = currentAngle + SMOOTHING_FACTOR * difference
        if (result < 0) result += 360
        if (result >= 360) result -= 360
        return result
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
            var newAzimuth = Math.toDegrees(azimuthRadians.toDouble()).toFloat()
            if (newAzimuth < 0) newAzimuth += 360f

            _azimuth.floatValue = smoothAngle(_azimuth.floatValue, newAzimuth)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No lo necesitamos para este juego, pero el método es obligatorio.
    }
}