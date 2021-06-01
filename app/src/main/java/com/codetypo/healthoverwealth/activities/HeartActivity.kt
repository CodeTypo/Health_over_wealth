package com.codetypo.healthoverwealth.activities

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.codetypo.healthoverwealth.R
import kotlinx.android.synthetic.main.activity_heart.*

class HeartActivity : AppCompatActivity(), SensorEventListener {
    var sensorMgr: SensorManager? = null
    var heartRate : Sensor? = null;
    var heartTV: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heart)
        heartTV = heartMonitorTV
        sensorMgr = this.getSystemService(SENSOR_SERVICE) as SensorManager
        heartRate = sensorMgr!!.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        Log.d("HR_ACTIV","CREATED")


    }

    override fun onResume() {
        super.onResume()
        sensorMgr!!.registerListener(this, heartRate, 2000000)
    }

    override fun onPause() {
        super.onPause()
        sensorMgr!!.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            Log.d("SENSOR", event.values[0].toString())
            heartTV?.text = event.values[0].toString()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("ACC","changed")
    }
}