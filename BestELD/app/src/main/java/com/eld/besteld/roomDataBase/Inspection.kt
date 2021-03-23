package com.eld.besteld.roomDataBase

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity
data class Inspection(

    @PrimaryKey
    val latitude: Double,

    val location: String?=null,

    val longitude: Double?=null,

    val notes: String?=null,

    val type: String?=null
) : Parcelable