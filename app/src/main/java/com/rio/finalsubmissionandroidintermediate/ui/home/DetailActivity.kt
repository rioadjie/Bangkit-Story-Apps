package com.rio.finalsubmissionandroidintermediate.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rio.finalsubmissionandroidintermediate.databinding.ActivityDetailBinding
import com.rio.finalsubmissionandroidintermediate.live_data.response.ListStoryItem
import com.rio.finalsubmissionandroidintermediate.ui.data.adapter.AdapterHome

class DetailActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: AdapterHome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = AdapterHome()
        supportActionBar?.hide()

        @Suppress("DEPRECATION")
        val detailStory = intent.getParcelableExtra<ListStoryItem>(DETAIL_STORY)
        dataStorys(detailStory)

        binding.iconBack.setOnClickListener {
            finish()
        }


    }


    private fun dataStorys(storyItem: ListStoryItem?) {
        storyItem?.let {
            binding.tvName.text = it.name
            binding.tvDescription.text = it.description
            binding.imgPhotos.setImageResource(0)
            Glide.with(binding.imgPhotos).load(it.photoUrl).into(binding.imgPhotos)
        }
    }


    companion object {
        const val DETAIL_STORY = "story_detail"
    }
}