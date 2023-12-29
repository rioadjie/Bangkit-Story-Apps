package com.rio.finalsubmissionandroidintermediate.live_data.remote

import android.content.Context
import com.rio.finalsubmissionandroidintermediate.live_data.pref.LiveUserRepository
import com.rio.finalsubmissionandroidintermediate.live_data.pref.manager.Preference
import com.rio.finalsubmissionandroidintermediate.live_data.pref.manager.dataStore
import com.rio.finalsubmissionandroidintermediate.live_data.pref.repository.DataStoryRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injections {
    fun provideRepository(context: Context): LiveUserRepository {
        val pref = Preference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiClient.getApiService(user.token)
        return LiveUserRepository.getInstance(apiService, pref)
    }

    fun provideStoryRepository(context: Context): DataStoryRepository {
        val pref = Preference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiClient.getApiService(user.token)
        return DataStoryRepository.getInstance(apiService, pref)
    }
}