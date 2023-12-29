package com.rio.finalsubmissionandroidintermediate.ui.data.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rio.finalsubmissionandroidintermediate.databinding.CardStoryBinding
import com.rio.finalsubmissionandroidintermediate.live_data.response.ListStoryItem
import com.rio.finalsubmissionandroidintermediate.ui.home.DetailActivity

class AdapterHome :
    PagingDataAdapter<ListStoryItem, AdapterHome.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CardStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val story = getItem(position)
        if (story != null) {
            holder.bind(story)
        }
    }

    inner class MyViewHolder(val binding: CardStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(story: ListStoryItem) {
            binding.usernameStory.text = story.name
            binding.descriptionStory.text = story.description
            binding.imgPhoto.setImageResource(0)
            Glide.with(binding.imgPhoto).load(story.photoUrl).into(binding.imgPhoto)
            itemView.setOnClickListener {
                val position = Intent(itemView.context, DetailActivity::class.java)
                position.putExtra(DetailActivity.DETAIL_STORY, story)
                itemView.context.startActivity(position)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}