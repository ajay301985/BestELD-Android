package com.eld.besteld.roomDataBase

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "Eld")
data class Eld(

    @PrimaryKey @NonNull
    @SerializedName("eldId")
    val eldId: String,

    @SerializedName("fleetDotNumber")
    val fleetDotNumber: String,

    @SerializedName("macAddress")
    val macAddress: String?=null,

    @SerializedName("remarks")
    val remarks: String?=null,

    @SerializedName("status")
    val status: String?=null
)