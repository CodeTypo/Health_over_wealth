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
import com.github.mikephil.charting.components.*
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

/**
 * This class represents activity for heart rate.
 */
class HeartActivity : AppCompatActivity(), SensorEventListener {
    var sensorMgr: SensorManager? = null
    var heartRate: Sensor? = null
    var heartTV: TextView? = null
    var hintTV: TextView? = null
    var entryValue = 80

    var stage1 = false
    var stage2 = false
    var oldVal = 0f
    val database = FirebaseDatabase.getInstance()
    val uid = FirebaseAuth.getInstance().currentUser?.uid

    /**
     * This function is called when HeartActivity is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heart)
        heartTV = heartMonitorTV
        hintTV = hrHintTv
        sensorMgr = this.getSystemService(SENSOR_SERVICE) as SensorManager
        heartRate = sensorMgr!!.getDefaultSensor(Sensor.TYPE_HEART_RATE)

        val database = FirebaseDatabase.getInstance()

        stage1 = false
        stage2 = false

        val uid = FirebaseAuth.getInstance().currentUser?.uid

        val heartRateModel =
            database.reference.child(uid.toString())

        heartRateModel.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    if (snapshot.exists()) {
                        val heartRateValue = snapshot.child("HEART_RATE_MODEL")
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
        legend.isEnabled = true
        val xl = heart_chart.xAxis
        xl.textColor = Color.BLACK
        xl.setDrawGridLines(false)
        xl.setAvoidFirstLastClipping(true)
        xl.axisMinimum = 0f
        val lE = LegendEntry()
        lE.label = "time [s]"
        val l: Legend = heart_chart.legend
        l.setCustom(listOf(lE));
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        xl.position = XAxis.XAxisPosition.BOTTOM;
        xl.isEnabled = true

        val ll = LimitLine(110f, "High HR")
        ll.lineColor = Color.RED;
        ll.lineWidth = 1f;
        ll.textColor = Color.BLACK;
        ll.textSize = 12f;
        val leftAxis = heart_chart.axisLeft
        leftAxis.textColor = Color.BLACK
        leftAxis.axisMinimum = 25f
        leftAxis.axisMaximum = 150f
        leftAxis.addLimitLine(ll)
        leftAxis.setDrawGridLines(false)
        leftAxis.isEnabled = true

        val d = Description()
        d.text = ""
        heart_chart.description = d

        val rightAxis = heart_chart.axisRight
        rightAxis.isEnabled = false
    }

    /**
     * This function creates set of data which is later being updated with heart rate values acquired from the heart rate sensor.
     */
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

    /**
     * This function is called when HeartActivity is resumed.
     */
    override fun onResume() {
        super.onResume()
        sensorMgr!!.registerListener(this, heartRate, 100000)
        stage1 = false
        stage2 = false
    }

    /**
     * This function is called when HeartActivity is paused.
     */
    override fun onPause() {
        super.onPause()
        sensorMgr!!.unregisterListener(this)
        stage1 = false
        stage2 = false
    }

    /**
     * This function is called when the sensor changes its value.
     */
    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            heartTV?.text = event.values[0].roundToInt().toString()


            if (stage1) {
                hintTV?.text = "Stage 2"
            }

            if (stage2) {
                hintTV?.text =
                    "All good! Measure your heart rate for about 10 seconds to let it stabilize. Afterwards, you can remove your finger and the heart rate will be saved!"
            }


            val data: LineData = heart_chart.data
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
                database.reference.child(uid.toString()).child("HEART_RATE_MODEL")
                    .child("heart_rate")

            heartRateModel.setValue(entryValue.toString())

            data.addEntry((Entry(set.entryCount.toFloat(),
                entryValue.toFloat())), 0)
            heartMonitorTV.text = entryValue.toString()
            data.notifyDataChanged()
            heart_chart.notifyDataSetChanged()

            heart_chart.setVisibleXRangeMaximum(20F)

            if (oldVal != 0f && oldVal != entryValue.toFloat())
                if (stage1 == false)
                    stage1 = true

            if (oldVal != 0f && oldVal.toInt() == entryValue.toInt())
                stage2 = true
            stage1 = false

            heart_chart.moveViewToX(data.entryCount.toFloat())
            oldVal = entryValue.toFloat()

        }
    }

    /**
     * This function is called when the accuracy of the sensors has changed.
     */
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}