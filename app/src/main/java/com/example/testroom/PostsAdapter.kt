package com.example.testroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testroom.databinding.PostItemBinding

class PostsAdapter:ListAdapter<Post , PostsAdapter.PostViewHolder>(DiffCallback) {


    class PostViewHolder(val binding:PostItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Post){
            binding.idItem.text = item.id.toString()
            binding.useridItem.text = item.userId.toString()
            binding.descItem.text = item.body
        }
    }
    companion object {
       private val DiffCallback = object: DiffUtil.ItemCallback<Post>() {
           override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id == newItem.id
           }

           override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
               return oldItem == newItem
           }

       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(PostItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}