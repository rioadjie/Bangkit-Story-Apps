package com.rio.finalsubmissionandroidintermediate.ui.data.model

import androidx.lifecycle.ViewModel
import com.rio.finalsubmissionandroidintermediate.live_data.pref.manager.ModelUser
import com.rio.finalsubmissionandroidintermediate.live_data.pref.LiveUserRepository

class LoginModel(private val repository: LiveUserRepository) : ViewModel() {
    fun saveSession(user: ModelUser) {
        repository.saveSession(user)
    }

    fun login(email: String, password: String) = repository.loginUser(email, password)
}