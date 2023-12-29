package com.rio.finalsubmissionandroidintermediate.live_data.response

import com.google.gson.annotations.SerializedName

data class ResponseStory(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
