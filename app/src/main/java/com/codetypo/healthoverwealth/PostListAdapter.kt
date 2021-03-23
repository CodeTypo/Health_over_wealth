package com.codetypo.healthoverwealth

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_with_desc.view.*

private const val POST_TYPE_DESC = 0
private const val POST_TYPE_IMG = 1

class PostListAdapter (var postListItems:List<PostModel>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class DescViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(postModel: PostModel){
            itemView.desc_post_title.text = postModel.name
            itemView.desc_post_desc.text = postModel.last_name
        }
    }

    class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(postModel: PostModel){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //View holders for all type of views
        if(viewType == POST_TYPE_DESC){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_with_desc,parent,false)
            return DescViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_with_image,parent,false)
            return ImageViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(postListItems[position].mode == 0L){
            POST_TYPE_DESC
        } else {
            POST_TYPE_IMG
        }
    }

    override fun getItemCount(): Int {
        return postListItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position)== POST_TYPE_DESC){
            (holder as DescViewHolder).bind(postListItems[position])
        } else {
            (holder as ImageViewHolder).bind(postListItems[position])
        }
    }
}