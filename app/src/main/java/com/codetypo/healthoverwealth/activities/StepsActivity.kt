package com.codetypo.healthoverwealth.activities

import android.graphics.Color.red
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codetypo.healthoverwealth.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.*
import kotlinx.android.synthetic.main.activity_steps.*

class StepsActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_steps)

            setBarChart()
        }

        private fun setBarChart() {
            val entries = ArrayList<BarEntry>()
            entries.add(BarEntry(8f, 0f))
            entries.add(BarEntry(2f, 1f))
            entries.add(BarEntry(5f, 2f))
            entries.add(BarEntry(20f, 3f))
            entries.add(BarEntry(15f, 4f))
            entries.add(BarEntry(19f, 5f))

            val barDataSet = BarDataSet(entries, "Cells")

            val data = BarData(barDataSet)
            barChart.data = data // set the data and list of lables into chart
            //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
            barDataSet.color = resources.getColor(R.color.black)

            barChart.animateY(5000)
        }
    }
