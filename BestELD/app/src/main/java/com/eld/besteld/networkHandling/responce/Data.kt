package com.eld.besteld.networkHandling.responce

import androidx.room.Entity
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

@Entity
data class Data (
    @SerializedName("model")
    @Expose
    var model: String? = null,

    @SerializedName("regNumber")
    @Expose
    var regNumber: String? = null,

    @SerializedName("fleetDOTNumber")
    @Expose
    var fleetDOTNumber: String? = null,
    @SerializedName("policyNumber")
    @Expose
    var policyNumber: String? = null,

    @SerializedName("carrierName")
    @Expose
    var carrierName: String? = null,

    @SerializedName("liabilityInsurance")
    @Expose
    var liabilityInsurance: String? = null,

    @SerializedName("make")
    @Expose
    var make: String? = null,

    @SerializedName("expiryDate")
    @Expose
    var expiryDate: String? = null,

    @SerializedName("cargoInsurance")
    @Expose
    var cargoInsurance: String? = null,

    @SerializedName("vin")
    @Expose
    var vin: String? = null,

    @SerializedName("year")
    @Expose
    var year: String? = null,

    @SerializedName("fuelType")
    @Expose
    var fuelType: String? = null,

    @SerializedName("registration")
    @Expose
    var registration: String? = null,

    @SerializedName("id")
    @Expose
    var id: String? = null,

    @SerializedName("truckNumber")
    @Expose
    var truckNumber: String? = null,

    @SerializedName("odometer")
    @Expose
    var odometer: String? = null,

    @SerializedName("licensePlate")
    @Expose
    var licensePlate: String? = null

)