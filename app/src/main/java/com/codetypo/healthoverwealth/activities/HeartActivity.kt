package com.codetypo.healthoverwealth.activities

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.codetypo.healthoverwealth.R
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_heart.*
import kotlin.math.roundToInt


class HeartActivity : AppCompatActivity(), SensorEventListener {
    var sensorMgr: SensorManager? = null
    var heartRate: Sensor? = null
    var heartTV: TextView? = null
    var thread: Thread? = null
    var entryValue = 80


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heart)
        heartTV = heartMonitorTV
        sensorMgr = this.getSystemService(SENSOR_SERVICE) as SensorManager
        heartRate = sensorMgr!!.getDefaultSensor(Sensor.TYPE_HEART_RATE)

        val database = FirebaseDatabase.getInstance()

        val uid = FirebaseAuth.getInstance().currentUser?.uid

        val heartRateModel =
            database.reference.child(uid.toString())

        heartRateModel.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    if (snapshot.exists()) {
                        val heartRateValue = snapshot.child("HeartRateModel")
                        entryValue = heartRateValue.getValue(String::class.java).toString().toInt()
                    }
                } catch (e: Exception) {
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })


        val data = LineData()
        data.setValueTextColor(Color.RED)
        heart_chart.data = data;
        val legend = heart_chart.legend
        legend.form = Legend.LegendForm.LINE
        legend.textColor = Color.BLACK
        legend.isEnabled = false
        val xl = heart_chart.xAxis
        xl.textColor = Color.BLACK
        xl.setDrawGridLines(false)
        xl.setAvoidFirstLastClipping(true)
        xl.isEnabled = false

        val leftAxis = heart_chart.axisLeft
        leftAxis.textColor = Color.BLACK
        leftAxis.axisMinimum = 25f
        leftAxis.axisMaximum = 150f
        leftAxis.setDrawGridLines(false)
        leftAxis.isEnabled = false

        val d = Description()
        d.text = ""
        heart_chart.description = d

        val rightAxis = heart_chart.axisRight
        rightAxis.isEnabled = false
    }

    private fun createSet(): LineDataSet {
        val set = LineDataSet(null, "Heart rate")
        set.mode = LineDataSet.Mode.CUBIC_BEZIER
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.color = Color.RED
        set.setCircleColor(Color.RED)
        set.lineWidth = 3f
        set.circleRadius = 0f
        set.fillAlpha = 65
        set.fillColor = Color.RED
        set.highLightColor = Color.rgb(244, 117, 117)
        set.valueTextColor = Color.WHITE
        set.valueTextSize = 0f
        set.setDrawValues(false)
        return set
    }

    override fun onResume() {
        super.onResume()
        sensorMgr!!.registerListener(this, heartRate, 100000)
    }

    override fun onPause() {
        super.onPause()
        sensorMgr!!.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            heartTV?.text = event.values[0].roundToInt().toString()


            val data: LineData = heart_chart.data
            if (data != null) {
                var set = data.getDataSetByIndex(0)
                if (set == null) {
                    set = createSet()
                    data.addDataSet(set)
                }

                if (event.values[0].toInt() > 0) {
                    entryValue = event.values[0].toInt()
                }

                val database = FirebaseDatabase.getInstance()

                val uid = FirebaseAuth.getInstance().currentUser?.uid

                val heartRateModel =
                    database.reference.child(uid.toString()).child("HeartRateModel")

                heartRateModel.setValue(entryValue.toString())

                data.addEntry((Entry(set.entryCount.toFloat(),
                    entryValue.toFloat())), 0)
                heartMonitorTV.text = entryValue.toString()
                data.notifyDataChanged()

                heart_chart.notifyDataSetChanged()

                heart_chart.setVisibleXRangeMaximum(20F)

                heart_chart.moveViewToX(data.entryCount.toFloat())
            }

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("ACC", "changed")
    }
}