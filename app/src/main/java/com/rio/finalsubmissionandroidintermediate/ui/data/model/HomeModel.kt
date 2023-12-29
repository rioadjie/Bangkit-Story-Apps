package com.rio.finalsubmissionandroidintermediate.ui.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rio.finalsubmissionandroidintermediate.live_data.pref.manager.ModelUser
import com.rio.finalsubmissionandroidintermediate.live_data.pref.repository.DataStoryRepository
import com.rio.finalsubmissionandroidintermediate.live_data.response.ListStoryItem
import kotlinx.coroutines.launch

class HomeModel(private val dataStoryRepository: DataStoryRepository) : ViewModel() {

    val getStory: LiveData<PagingData<ListStoryItem>> =
        dataStoryRepository.getStory().cachedIn(viewModelScope)

    fun getSession(): LiveData<ModelUser> {
        return dataStoryRepository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            dataStoryRepository.logout()
        }

    }
}