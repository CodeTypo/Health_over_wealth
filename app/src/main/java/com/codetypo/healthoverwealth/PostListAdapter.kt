package com.codetypo.healthoverwealth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

private const val TILE_WATER = 0
private const val TILE_BMI = 1
private const val TILE_STEPS = 2
private const val TILE_HEARTRATE = 3
private const val TILE_WEIGHT = 4

//class PostListAdapter (var postListItems:List<PostModel>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
class PostListAdapter (var tileOrder: Array<String>, private val tileClickListener: TileClickListener):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class TileViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(){
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //View holders for all type of views
        if(viewType == TILE_WATER){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.water_tile,parent,false)
            return TileViewHolder(view)
        } else if(viewType == TILE_WEIGHT){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.weight_tile,parent,false)
            return TileViewHolder(view)
        }
        else if (viewType == TILE_BMI) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.bmi_tile,parent,false)
            return TileViewHolder(view)
        } else if (viewType == TILE_HEARTRATE) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.heartrate_tile,parent,false)
            return TileViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.steps_tile,parent,false)
            return TileViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(tileOrder[position] == "water"){
            TILE_WATER
        } else if (tileOrder[position] == "bmi") {
            TILE_BMI
        } else if (tileOrder[position] == "heartrate"){
            TILE_HEARTRATE
        } else if(tileOrder[position] == "weight"){
            TILE_WEIGHT
        }
        else TILE_STEPS
    }

    override fun getItemCount(): Int {
        return tileOrder.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener{tileClickListener.onTileClickListener(position)}
    }

}