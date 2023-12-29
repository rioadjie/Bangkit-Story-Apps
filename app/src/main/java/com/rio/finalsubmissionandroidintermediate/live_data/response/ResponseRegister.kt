package com.rio.finalsubmissionandroidintermediate.live_data.response

import com.google.gson.annotations.SerializedName

data class ResponseRegister(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
