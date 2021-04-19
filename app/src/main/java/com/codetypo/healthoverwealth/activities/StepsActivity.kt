package com.codetypo.healthoverwealth.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codetypo.healthoverwealth.R
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
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
            l.setCustom(listOf(l1,l2,l3,l4,l5,l6,l7));
            l.xEntrySpace = 10f; // space between the legend entries on the x-axis
        }
    }
