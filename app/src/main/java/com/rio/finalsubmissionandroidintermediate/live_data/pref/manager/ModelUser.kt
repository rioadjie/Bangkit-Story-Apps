package com.rio.finalsubmissionandroidintermediate.live_data.pref.manager

data class ModelUser(
    val name: String,
    val userId: String,
    val token: String,
    val isLogin: Boolean = false
)
