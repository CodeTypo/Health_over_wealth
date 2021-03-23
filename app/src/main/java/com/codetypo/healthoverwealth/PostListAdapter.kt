package com.codetypo.healthoverwealth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

private const val TILE_WATER = 0
private const val TILE_BMI = 1
private const val TILE_STEPS = 2

//class PostListAdapter (var postListItems:List<PostModel>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
class PostListAdapter (var tileOrder: Array<String>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class DescViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(postModel: PostModel){
//            itemView.desc_post_title.text = postModel.name
//            itemView.desc_post_desc.text = postModel.last_name
        }
    }

    class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(postModel: PostModel){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //View holders for all type of views
        if(viewType == TILE_WATER){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.water_tile,parent,false)
            return DescViewHolder(view)
        } else if (viewType == TILE_BMI) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.bmi_tile,parent,false)
            return ImageViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.steps_tile,parent,false)
            return ImageViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(tileOrder[position] == "water"){
            return TILE_WATER
        } else if (tileOrder[position] == "bmi") {
            return TILE_BMI
        } else return  TILE_STEPS
    }

    override fun getItemCount(): Int {
        return tileOrder.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position)== TILE_WATER){
           // (holder as DescViewHolder).bind(postListItems[position])
        } else {
          //  (holder as ImageViewHolder).bind(postListItems[position])
        }
    }
}