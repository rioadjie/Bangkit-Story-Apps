package com.rio.finalsubmissionandroidintermediate.live_data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class HomeResponse(

	@field:SerializedName("listStory")
	val listStory: List<ListStoryItem> = emptyList(),

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

@Parcelize
data class ListStoryItem(
	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("photoUrl")
	val photoUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("lon")
	val lon: Double,

	@field:SerializedName("lat")
	val lat: Double,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,
) : Parcelable
