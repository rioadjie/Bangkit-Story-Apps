package com.rio.finalsubmissionandroidintermediate.ui.data.model

import androidx.lifecycle.ViewModel
import com.rio.finalsubmissionandroidintermediate.live_data.pref.repository.DataStoryRepository

class MapsModel(private val dataStoryRepository: DataStoryRepository) : ViewModel() {
    fun getLocation() = dataStoryRepository.getStoriesWithLocation()
}