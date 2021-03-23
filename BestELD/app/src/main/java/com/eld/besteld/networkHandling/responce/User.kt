package com.eld.besteld.networkHandling.responce
import com.google.gson.annotations.SerializedName

class User {
	@SerializedName("idToken")
	val idToken: IdToken? =null
	@SerializedName("refreshToken")
	val refreshToken: RefreshToken? =null
	@SerializedName("accessToken")
	val accessToken: AccessToken? =null
	@SerializedName("clockDrift")
	val clockDrift: Int? =null
}

