package com.rio.finalsubmissionandroidintermediate.live_data.remote

import com.rio.finalsubmissionandroidintermediate.live_data.response.HomeResponse
import com.rio.finalsubmissionandroidintermediate.live_data.response.ResponseLogin
import com.rio.finalsubmissionandroidintermediate.live_data.response.ResponseRegister
import com.rio.finalsubmissionandroidintermediate.live_data.response.ResponseStory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): ResponseRegister

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): ResponseLogin

    @GET("stories")
    suspend fun getStory(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): HomeResponse

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): ResponseStory

    @GET("stories")
    suspend fun getStoriesWithLocation(
        @Query("location") location: Int = 1,
    ): HomeResponse
}
