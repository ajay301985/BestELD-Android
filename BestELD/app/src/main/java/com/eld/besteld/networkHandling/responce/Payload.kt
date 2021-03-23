package com.eld.besteld.networkHandling.responce
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

 class Payload {

	 @SerializedName("sub")
	 @Expose
	 val sub: String? = null
	 @SerializedName("device_key")
	 val device_device_key: String?= null
	 @SerializedName("event_id")
	 val event_id: String? = null
	 @SerializedName("token_use")
	 val token_use: String? = null
	 @SerializedName("scope")
	 val scope: String? =null
	 @SerializedName("auth_time")
	 val auth_time: Int? = null
	 @SerializedName("iss")
	 val iss: String? =null
	 @SerializedName("exp")
	 val exp: Int? =null
	 @SerializedName("iat")
	 val iat: Int? =null
	 @SerializedName("jti")
	 val jti: String? =null
	 @SerializedName("client_id")
	 val client_id: String? =null
	 @SerializedName("username")
	 val username: String? =null

 }