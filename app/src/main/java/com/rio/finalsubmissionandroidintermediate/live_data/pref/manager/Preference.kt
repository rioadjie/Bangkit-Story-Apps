package com.rio.finalsubmissionandroidintermediate.live_data.pref.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class Preference private constructor(private val dataStore: DataStore<Preferences>) {


    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun saveSession(user: ModelUser) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = user.name
            preferences[USER_ID_KEY] = user.userId
            preferences[TOKEN_KEY] = user.token
            preferences[IS_LOGIN_KEY] = true
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: Preference? = null

        private val NAME_KEY = stringPreferencesKey("name")
        private val USER_ID_KEY = stringPreferencesKey("userId")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): Preference {
            return INSTANCE ?: synchronized(this) {
                val instance = Preference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    fun getSession(): Flow<ModelUser> {
        return dataStore.data.map { preferences ->
            ModelUser(
                preferences[NAME_KEY] ?: "",
                preferences[USER_ID_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }
}