package com.eld.besteld.networkHandling.responce

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class VinResponce (
    @SerializedName("success")
    @Expose
    var success: Boolean? = null,

    @SerializedName("message")
    @Expose
    var message: String? = null,

    @SerializedName("data")
    @Expose
    var data: Data? = null

)