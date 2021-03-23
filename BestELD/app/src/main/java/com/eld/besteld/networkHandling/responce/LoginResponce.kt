package com.eld.besteld.networkHandling.responce
import com.eld.besteld.roomDataBase.DriverInformation
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

 class LoginResponce {
	 @SerializedName("success")
	 @Expose
	 val success: String? =null
	 @SerializedName("message")
	 val message: String? =null
	 @SerializedName("user")
	 val user: User? =null
	 @SerializedName("profile")
	 val profile: DriverInformation? =null
 }



