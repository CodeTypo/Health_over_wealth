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


class StepsActivity : AppCompatActivity(), SensorEventListener {

    var running = false
    var sensorMgr: SensorManager? = null
    var stepsTV: TextView? = null
    var weeklySteps = 0f

    val database = FirebaseDatabase.getInstance()
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val stepsModel = database.reference.child(uid.toString()).child("StepsModel")
    var daysSteps = hashMapOf<String, String>()

    var preferences: SharedPreferences? = null
    var stepsMadeToday: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_steps)

        stepsTV = stepCounterTV
        sensorMgr = getSystemService(SENSOR_SERVICE) as SensorManager

        preferences = this.getSharedPreferences("PREFERENCES", MODE_PRIVATE)

        val database = FirebaseDatabase.getInstance()

        val uid = FirebaseAuth.getInstance().currentUser?.uid

        val stepsModel = database.reference.child(uid.toString()).child("StepsModel")

        stepsModel.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    if (snapshot.exists()) {
                        val stepsTargetValue = snapshot.child("StepsTarget")
                        weeklyGoalTV.text = stepsTargetValue.getValue(String::class.java).toString()
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
                Log.i("firebase", "Got value ${daysSteps}")
                setBarChart()
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
        }

    }

    private fun setBarChart() {
        val entries = ArrayList<BarEntry>()
        if (daysSteps.containsKey("MONDAY")) {
            daysSteps["MONDAY"]?.toDouble()?.let {
                weeklySteps += it.toFloat()
                BarEntry(1f, it.toFloat())
            }?.let {
                entries.add(it)
            }
        } else {
            entries.add(BarEntry(1f, 0f))
        }

        if (daysSteps.containsKey("TUESDAY")) {
            daysSteps["TUESDAY"]?.toDouble()?.let {
                weeklySteps += it.toFloat()
                BarEntry(2f, it.toFloat())
            }?.let {
                entries.add(it)
            }
        } else {
            entries.add(BarEntry(2f, 0f))
        }

        if (daysSteps.containsKey("WEDNESDAY")) {
            daysSteps["WEDNESDAY"]?.toDouble()?.let {
                weeklySteps += it.toFloat()
                BarEntry(3f, it.toFloat())
            }?.let {
                entries.add(it)
            }
        } else {
            entries.add(BarEntry(3f, 0f))
        }


        if (daysSteps.containsKey("THURSDAY")) {
            daysSteps["THURSDAY"]?.toDouble()?.let {
                weeklySteps += it.toFloat()
                BarEntry(4f, it.toFloat())
            }?.let {
                entries.add(it)
            }
        } else {
            entries.add(BarEntry(4f, 0f))
        }


        if (daysSteps.containsKey("FRIDAY")) {
            daysSteps["FRIDAY"]?.toDouble()?.let {
                weeklySteps += it.toFloat()
                BarEntry(5f, it.toFloat())
            }?.let {
                entries.add(it)
            }
        } else {
            entries.add(BarEntry(5f, 0f))
        }


        if (daysSteps.containsKey("SATURDAY")) {
            daysSteps["SATURDAY"]?.toDouble()?.let {
                weeklySteps += it.toFloat()
                BarEntry(6f, it.toFloat())
            }?.let {
                entries.add(it)
            }
        } else {
            entries.add(BarEntry(6f, 0f))
        }


        if (daysSteps.containsKey("SUNDAY")) {
            daysSteps.get("SUNDAY")?.toDouble()?.let {
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
        val l: Legend = barChart.getLegend()
        l.setCustom(listOf(l1, l2, l3, l4, l5, l6, l7));
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.xEntrySpace = 22f; // space between the legend entries on the x-axis

        val d = Description()
        d.text = ""
        barChart.description = d
        barChart.setDrawGridBackground(false);
        barChart.getDescription().setEnabled(false);
        barChart.setDrawBorders(false);

        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false)
    }

    override fun onResume() {
        super.onResume()
        running = true
        var stepsSensor = sensorMgr?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepsSensor == null) {

        } else {
            sensorMgr?.registerListener(this, stepsSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
        sensorMgr!!.unregisterListener(this)

        stepsModel.child("" + LocalDate.now().dayOfWeek).setValue(stepsMadeToday.toString())
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (running) {

            if(preferences!!.getBoolean("FIRST_LAUNCH", true)){
                val editor = preferences!!.edit()
                editor.putString("STEPS_DAY", LocalDate.now().dayOfWeek.toString())
                editor.putBoolean("FIRST_LAUNCH", false)
                editor.putInt("STEPS_SENSOR_VALUE", event.values[0].toInt())
                editor.apply()
            } else{
                if(LocalDate.now().dayOfWeek.toString() != preferences!!.getString("STEPS_DAY", "DAY").toString()){
                    val editor = preferences!!.edit()
                    editor.putString("STEPS_DAY", LocalDate.now().dayOfWeek.toString())
                    editor.putBoolean("FIRST_LAUNCH", false)
                    editor.putInt("STEPS_SENSOR_VALUE", event.values[0].toInt())
                    editor.apply()
                    stepsMadeToday = 0
                } else {
                    stepsMadeToday = event.values[0].toInt() - preferences!!.getInt("STEPS_SENSOR_VALUE",0)
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("ACC", "changed")
    }
}
