package com.rio.finalsubmissionandroidintermediate.ui.data.model

import androidx.lifecycle.ViewModel
import com.rio.finalsubmissionandroidintermediate.live_data.response.ResponseRegister
import com.rio.finalsubmissionandroidintermediate.live_data.pref.LiveUserRepository


class RegisterModel(private val liveUserRepository: LiveUserRepository) : ViewModel(){
    suspend fun register (name: String, email: String, password: String) : ResponseRegister {
        return liveUserRepository.registerUser(name, email, password)
    }
}