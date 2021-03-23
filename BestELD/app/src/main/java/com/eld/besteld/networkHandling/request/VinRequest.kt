package com.eld.besteld.networkHandling.request

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class VinRequest (
    @SerializedName("vin")
    @Expose
    var vin: String? = null

)