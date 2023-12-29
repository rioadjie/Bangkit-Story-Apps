package com.rio.finalsubmissionandroidintermediate.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rio.finalsubmissionandroidintermediate.live_data.pref.repository.DataStoryRepository
import com.rio.finalsubmissionandroidintermediate.live_data.remote.Injections
import com.rio.finalsubmissionandroidintermediate.live_data.pref.LiveUserRepository
import com.rio.finalsubmissionandroidintermediate.ui.data.model.HomeModel
import com.rio.finalsubmissionandroidintermediate.ui.data.model.IntroModel
import com.rio.finalsubmissionandroidintermediate.ui.data.model.LoginModel
import com.rio.finalsubmissionandroidintermediate.ui.data.model.MapsModel
import com.rio.finalsubmissionandroidintermediate.ui.data.model.RegisterModel
import com.rio.finalsubmissionandroidintermediate.ui.data.model.StoryModel

class FactoryView(private val liveUserRepository: LiveUserRepository, private val dataStoryRepository: DataStoryRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(IntroModel::class.java) -> {
                IntroModel(liveUserRepository) as T
            }
            modelClass.isAssignableFrom(LoginModel::class.java) -> {
                LoginModel(liveUserRepository) as T
            }
            modelClass.isAssignableFrom(HomeModel::class.java) -> {
                HomeModel(dataStoryRepository) as T
            }
            modelClass.isAssignableFrom(RegisterModel::class.java) -> {
                RegisterModel(liveUserRepository) as T
            }
            modelClass.isAssignableFrom(StoryModel::class.java) -> {
                StoryModel(dataStoryRepository) as T
            }
            modelClass.isAssignableFrom(MapsModel::class.java) -> {
                MapsModel(dataStoryRepository) as T
            }


            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: FactoryView? = null
        @JvmStatic
        fun getInstance(context: Context): FactoryView {
            if (INSTANCE == null) {
                synchronized(FactoryView::class.java) {
                    INSTANCE = FactoryView(Injections.provideRepository(context), Injections.provideStoryRepository(context))
                }
            }
            return INSTANCE as FactoryView
        }
        fun clearInstance() {
            INSTANCE = null
            LiveUserRepository.clearInstance()
        }
    }
}