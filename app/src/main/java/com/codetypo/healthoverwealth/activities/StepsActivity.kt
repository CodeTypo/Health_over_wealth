package com.codetypo.healthoverwealth.activities

import android.content.SharedPreferences
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
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_steps.*
import java.time.LocalDate

/**
 * This class represents activity for steps.
 */
class StepsActivity : AppCompatActivity(), SensorEventListener {

    var running = false
    var sensorMgr: SensorManager? = null
    var stepsTV: TextView? = null
    var weeklySteps = 0f

    val database = FirebaseDatabase.getInstance()
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val stepsModel = database.reference.child(uid.toString()).child("STEPS_MODEL")

    var daysSteps = hashMapOf<String, String>()

    var preferences: SharedPreferences? = null
    var stepsMadeToday: Int = 0

    /**
     * This function is called when StepsActivity is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_steps)

        stepsTV = stepCounterTV
        sensorMgr = getSystemService(SENSOR_SERVICE) as SensorManager

        preferences = this.getSharedPreferences("PREFERENCES", MODE_PRIVATE)

        stepsModel.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    if (snapshot.exists()) {
                        val stepsTargetValue =
                            snapshot.child("steps_target").getValue(String::class.java).toString()
                                .toInt()
                        weeklyGoalTV.text = (7 * stepsTargetValue).toString()
                    }
                } catch (e: Exception) {
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        if (stepCounterTV?.text.toString().toInt() >= Integer.parseInt(weeklyGoalTV.text.toString()
                .replace("\\s".toRegex(), ""))
        ) {
            goalProgressTV.text = "Goal accomplished,\n good job!"
            goalProgressTV.setTextColor(Color.parseColor("#3CB371"))
        }

        if (uid != null) {
            stepsModel.get().addOnSuccessListener {
                @Suppress("UNCHECKED_CAST")
                daysSteps = it.value as HashMap<String, String>
                Log.i("firebase", "Got value $daysSteps")
                setBarChart()
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
        }
    }

    /**
     * TThis function sets the values in the bar chart.
     */
    private fun setBarChart() {

        val entries = ArrayList<BarEntry>()
        if (daysSteps.containsKey("monday")) {
            daysSteps["monday"]?.toDouble()?.let {
                weeklySteps += it.toFloat()
                BarEntry(1f, it.toFloat())
            }?.let {
                entries.add(it)
            }
        } else {
            entries.add(BarEntry(1f, 0f))
        }

        if (daysSteps.containsKey("tuesday")) {
            daysSteps["tuesday"]?.toDouble()?.let {
                weeklySteps += it.toFloat()
                BarEntry(2f, it.toFloat())
            }?.let {
                entries.add(it)
            }
        } else {
            entries.add(BarEntry(2f, 0f))
        }

        if (daysSteps.containsKey("wednesday")) {
            daysSteps["wednesday"]?.toDouble()?.let {
                weeklySteps += it.toFloat()
                BarEntry(3f, it.toFloat())
            }?.let {
                entries.add(it)
            }
        } else {
            entries.add(BarEntry(3f, 0f))
        }


        if (daysSteps.containsKey("thursday")) {
            daysSteps["thursday"]?.toDouble()?.let {
                weeklySteps += it.toFloat()
                BarEntry(4f, it.toFloat())
            }?.let {
                entries.add(it)
            }
        } else {
            entries.add(BarEntry(4f, 0f))
        }


        if (daysSteps.containsKey("friday")) {
            daysSteps["friday"]?.toDouble()?.let {
                weeklySteps += it.toFloat()
                BarEntry(5f, it.toFloat())
            }?.let {
                entries.add(it)
            }
        } else {
            entries.add(BarEntry(5f, 0f))
        }


        if (daysSteps.containsKey("saturday")) {
            daysSteps["saturday"]?.toDouble()?.let {
                weeklySteps += it.toFloat()
                BarEntry(6f, it.toFloat())
            }?.let {
                entries.add(it)
            }
        } else {
            entries.add(BarEntry(6f, 0f))
        }


        if (daysSteps.containsKey("sunday")) {
            daysSteps["sunday"]?.toDouble()?.let {
                weeklySteps += it.toFloat()
                BarEntry(7f, it.toFloat())
            }?.let {
                entries.add(it)
            }
        } else {
            entries.add(BarEntry(7f, 0f))
        }

        stepsTV?.text = weeklySteps.toInt().toString()

        if (weeklySteps.toInt() >= Integer.parseInt(weeklyGoalTV.text.toString()
                .replace("\\s".toRegex(), ""))
        ) {
            goalProgressTV.text = "Goal accomplished,\n good job!"
            goalProgressTV.setTextColor(Color.parseColor("#3CB371"))

        }

        val barDataSet = BarDataSet(entries, "Cells")

        val data = BarData(barDataSet)
        barChart.data = data
        barDataSet.color = resources.getColor(R.color.teal_200)

        val left: YAxis = barChart.axisLeft
        left.setDrawAxisLine(false) // no axis line
        left.setDrawGridLines(false) // no grid lines

        val right: YAxis = barChart.axisRight
        right.setDrawAxisLine(false) // no axis line
        right.setDrawGridLines(false) // no grid lines

        val xAxis: XAxis = barChart.xAxis
        xAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(false);

        barChart.animateY(3000)
        val l1 = LegendEntry()
        l1.label = "mon"
        val l2 = LegendEntry()
        l2.label = "tue"
        val l3 = LegendEntry()
        l3.label = "wed"
        val l4 = LegendEntry()
        l4.label = "thu"
        val l5 = LegendEntry()
        l5.label = "fri"
        val l6 = LegendEntry()
        l6.label = "sat"
        val l7 = LegendEntry()
        l7.label = "sun"
        val l: Legend = barChart.legend
        l.setCustom(listOf(l1, l2, l3, l4, l5, l6, l7));
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.xEntrySpace = 22f // space between the legend entries on the x-axis

        val d = Description()
        d.text = ""
        barChart.description = d
        barChart.setDrawGridBackground(false)
        barChart.description.isEnabled = false
        barChart.setDrawBorders(false)

        barChart.axisLeft.isEnabled = false
        barChart.axisRight.isEnabled = false
    }

    /**
     * This function is called when StepsActivity is resumed.
     */
    override fun onResume() {
        super.onResume()
        running = true
        var stepsSensor = sensorMgr?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepsSensor == null) {

        } else {
            sensorMgr?.registerListener(this, stepsSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    /**
     * This function is called when StepsActivity is paused.
     */
    override fun onPause() {
        super.onPause()
        running = false
        sensorMgr!!.unregisterListener(this)

        stepsModel.child(LocalDate.now().dayOfWeek.toString().toLowerCase())
            .setValue(stepsMadeToday.toString())
    }

    /**
     * This function is called when the sensor changes its value.
     */
    override fun onSensorChanged(event: SensorEvent) {
        if (running) {

            if (preferences!!.getBoolean("FIRST_LAUNCH", true)) {
                val editor = preferences!!.edit()
                editor.putString("STEPS_DAY", LocalDate.now().dayOfWeek.toString())
                editor.putBoolean("FIRST_LAUNCH", false)
                editor.putInt("STEPS_SENSOR_VALUE", event.values[0].toInt())
                editor.apply()
            } else {
                if (LocalDate.now().dayOfWeek.toString() != preferences!!.getString("STEPS_DAY",
                        "DAY").toString()
                ) {
                    val editor = preferences!!.edit()
                    editor.putString("STEPS_DAY", LocalDate.now().dayOfWeek.toString())
                    editor.putBoolean("FIRST_LAUNCH", false)
                    editor.putInt("STEPS_SENSOR_VALUE", event.values[0].toInt())
                    editor.apply()
                }
            }

            stepsMadeToday =
                event.values[0].toInt() - preferences!!.getInt("STEPS_SENSOR_VALUE", 0)
        }
    }

    /**
     * This function is called when the accuracy of the sensors has changed.
     */
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}