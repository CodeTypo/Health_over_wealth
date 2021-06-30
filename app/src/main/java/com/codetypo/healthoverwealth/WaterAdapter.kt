package com.codetypo.healthoverwealth

import DailyWaterView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * This class represents WaterAdapter responsible for filling the successive daily drunk water rows
 * with input from database or given by the user.
 */
class WaterAdapter(private val dailyWaterList: List<DailyWaterView>) :
    RecyclerView.Adapter<WaterAdapter.ViewHolder>() {

    /**
     * This function is called when ViewHolder is created.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.daily_water_row, parent, false)

        return ViewHolder(view)
    }

    /**
     * This function is used to bind the list items to our widgets.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dailyWaterView = dailyWaterList[position]

        if (dailyWaterView.drunkWater.toInt() >= dailyWaterView.waterTarget.toInt())
            holder.imageView.setImageResource(R.drawable.water_bottle)
        else
            holder.imageView.setImageResource(R.drawable.desert)

        holder.day.text = dailyWaterView.day
        holder.drunkCups.text = dailyWaterView.drunkCups
        holder.drunkWater.text = dailyWaterView.drunkWater
        holder.cupsTarget.text = dailyWaterView.cupsTarget
        holder.waterTarget.text = dailyWaterView.waterTarget
    }

    /**
     * This function returns size of dailyWaterList.
     */
    override fun getItemCount(): Int {
        return dailyWaterList.size
    }

    /**
     * This class represents ViewHolder.
     */
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image)
        val day: TextView = itemView.findViewById(R.id.tvDay)
        val drunkCups: TextView = itemView.findViewById(R.id.tvDrunkCups)
        val drunkWater: TextView = itemView.findViewById(R.id.tvDrunkWater)
        val cupsTarget: TextView = itemView.findViewById(R.id.tvCupsTarget)
        val waterTarget: TextView = itemView.findViewById(R.id.tvWaterTarget)
    }
}