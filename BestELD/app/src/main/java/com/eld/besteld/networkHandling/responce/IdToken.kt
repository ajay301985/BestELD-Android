package com.eld.besteld.networkHandling.responce
import com.google.gson.annotations.SerializedName

 class IdToken {

	@SerializedName("jwtToken")
	val jwtToken: String? =null
	@SerializedName("payload")
	val payload: Payload? =null
}