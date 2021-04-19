package com.codetypo.healthoverwealth.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codetypo.healthoverwealth.R
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
            entries.add(BarEntry(1f, 10000f))
            entries.add(BarEntry(2f, 12000f))
            entries.add(BarEntry(3f, 8000f))
            entries.add(BarEntry(4f, 13125f))
            entries.add(BarEntry(5f, 4500f))
            entries.add(BarEntry(6f, 5750f))
            entries.add(BarEntry(7f, 13000f))

            val barDataSet = BarDataSet(entries, "Cells")

            val data = BarData(barDataSet)
            barChart.data = data // set the data and list of lables into chart
            //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
            barDataSet.color = resources.getColor(R.color.black)

            barChart.animateY(5000)
        }
    }
