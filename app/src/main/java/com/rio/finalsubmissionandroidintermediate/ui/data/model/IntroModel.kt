package com.rio.finalsubmissionandroidintermediate.ui.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rio.finalsubmissionandroidintermediate.live_data.pref.LiveUserRepository
import com.rio.finalsubmissionandroidintermediate.live_data.pref.manager.ModelUser


class IntroModel(private val repository: LiveUserRepository) : ViewModel() {
    fun getSession(): LiveData<ModelUser> {
        return repository.getSession().asLiveData()
    }

}