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
import kotlinx.android.synthetic.main.activity_heart.*
import kotlinx.android.synthetic.main.activity_steps.*
import kotlin.math.roundToInt


class HeartActivity : AppCompatActivity(), SensorEventListener {
    var sensorMgr: SensorManager? = null
    var heartRate : Sensor? = null;
    var heartTV: TextView? = null
    var thread: Thread? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heart)
        heartTV = heartMonitorTV
        sensorMgr = this.getSystemService(SENSOR_SERVICE) as SensorManager
        heartRate = sensorMgr!!.getDefaultSensor(Sensor.TYPE_HEART_RATE);


        val data = LineData()
        data.setValueTextColor(Color.RED)
        heart_chart.data = data;
        val legend = heart_chart.legend
        legend.form= Legend.LegendForm.LINE
        legend.textColor = Color.BLACK
        legend.isEnabled = false
        val xl = heart_chart.xAxis
        xl.textColor = Color.BLACK
        xl.setDrawGridLines(false)
        xl.setAvoidFirstLastClipping(true)
        xl.isEnabled = false

        val leftAxis =heart_chart.axisLeft
        leftAxis.textColor = Color.BLACK
        leftAxis.axisMinimum = 25f
        leftAxis.axisMaximum = 150f
        leftAxis.setDrawGridLines(false)
        leftAxis.isEnabled = false

        val d = Description()
        d.text=""
        heart_chart.description= d

        val rightAxis = heart_chart.axisRight
        rightAxis.isEnabled = false

//        feedMultiple()

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


//    private fun addEntry() {
//        val data: LineData = heart_chart.getData()
//        if (data != null) {
//            var set = data.getDataSetByIndex(0)
//            if (set == null) {
//                set = createSet()
//                data.addDataSet(set)
//            }
//
//            val entryvalue = ((Math.random() * 90) + 30f)
//
//            data.addEntry((Entry(set.entryCount.toFloat(),
//                entryvalue.toFloat())),0)
//            heartMonitorTV.text = entryvalue.toInt().toString()
//            data.notifyDataChanged()
//
//            // let the graph know it's data has changed
//            heart_chart.notifyDataSetChanged()
//
//            // limit the number of visible entries
//            heart_chart.setVisibleXRangeMaximum(20F)
//
//            // move to the latest entry
//            heart_chart.moveViewToX(data.entryCount.toFloat())
//        }
//    }

//    private fun feedMultiple() {
//        thread?.interrupt()
//        val runnable = Runnable { addEntry() }
//        thread = Thread {
//            for (i in 0..999) {
//
//                // Don't generate garbage runnables inside the loop.
//                runOnUiThread(runnable)
//                try {
//                    Thread.sleep(30)
//                } catch (e: InterruptedException) {
//                    e.printStackTrace()
//                }
//            }
//        }
//        thread!!.start()
//    }

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

                val entryvalue = (event.values[0])

                data.addEntry((Entry(set.entryCount.toFloat(),
                    entryvalue.toFloat())),0)
                heartMonitorTV.text = entryvalue.toInt().toString()
                data.notifyDataChanged()

                // let the graph know it's data has changed
                heart_chart.notifyDataSetChanged()

                // limit the number of visible entries
                heart_chart.setVisibleXRangeMaximum(20F)

                // move to the latest entry
                heart_chart.moveViewToX(data.entryCount.toFloat())
            }

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("ACC","changed")
    }
}