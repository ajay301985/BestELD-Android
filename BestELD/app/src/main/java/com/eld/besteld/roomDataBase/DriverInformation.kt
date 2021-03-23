package com.eld.besteld.roomDataBase

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class DriverInformation(

    val zip: String = "",

    val lastName: String = "",

    val strAddress1: String = "",

    val FleetDotNuber: String = "",

    val dlNumber: String = "",

    val strAddress2: String = "",

    val dlExpiryDate: String? = "",

    val email: String = "",

    val country: String = "",

    val primaryPhone: String = "",

    val firstName: String = "",

    val state: String = "",

    val city: String = "",

    val dlBackPic: String = "",

    val dlFrontPiv: String="",

    val secondaryPhone: String = "",

    @PrimaryKey
    val id: String = ""

) : Parcelable