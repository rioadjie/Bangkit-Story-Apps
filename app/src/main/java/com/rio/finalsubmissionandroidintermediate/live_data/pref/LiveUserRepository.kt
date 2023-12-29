package com.rio.finalsubmissionandroidintermediate.live_data.pref

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.rio.finalsubmissionandroidintermediate.live_data.pref.manager.ModelUser
import com.rio.finalsubmissionandroidintermediate.live_data.pref.manager.Preference
import com.rio.finalsubmissionandroidintermediate.live_data.pref.repository.Results
import com.rio.finalsubmissionandroidintermediate.live_data.pref.repository.DataStoryRepository
import com.rio.finalsubmissionandroidintermediate.live_data.remote.ApiService
import com.rio.finalsubmissionandroidintermediate.live_data.response.ResponseLogin
import com.rio.finalsubmissionandroidintermediate.live_data.response.ResponseRegister
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LiveUserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: Preference
) : CoroutineScope {

    fun saveSession(user: ModelUser) {
        launch(Dispatchers.IO) {
            userPreference.saveSession(user)
        }
    }

    fun getSession(): Flow<ModelUser> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun registerUser(name: String, email: String, password: String) : ResponseRegister {
        return apiService.register(name, email, password)
    }

    fun loginUser(email: String, password: String) : LiveData<Results<ResponseLogin>> = liveData {
        emit(Results.Loading)
        try {
            val result = apiService.login(email, password)
            if (result.error == false) {
                emit(Results.Success(result))
            } else {
                emit(Results.Error(result.message.toString()))
            }
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
    }


    companion object {
        @Volatile
        private var instance: LiveUserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: Preference
        ): LiveUserRepository =
            instance ?: synchronized(this) {
                instance ?: LiveUserRepository(apiService, userPreference)
            }.also { instance = it }

        fun clearInstance() {
            DataStoryRepository.instance = null
        }
    }

    override val coroutineContext: CoroutineContext get() = Dispatchers.Main
}