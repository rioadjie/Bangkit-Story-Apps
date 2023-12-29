package com.rio.finalsubmissionandroidintermediate.ui.data.model

import androidx.lifecycle.ViewModel
import com.rio.finalsubmissionandroidintermediate.live_data.pref.repository.DataStoryRepository
import java.io.File

class StoryModel(private val dataStoryRepository: DataStoryRepository) : ViewModel() {

    fun uploadImage(file: File, description: String) = dataStoryRepository.uploadImage(file, description)
}