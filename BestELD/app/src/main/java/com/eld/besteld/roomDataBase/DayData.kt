package com.eld.besteld.roomDataBase

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class DayData(

    var day: Long = 0,

    val dlNumber: String = "",

    val dutyStatus: String = "",

    val endLatitude: String = "",

    val endLocation: String = "",

    val endLongitude: String = "",

    val endOdometer: String = "",

    var endTime: String = "",

    var endTimeString: String = "",

    //val autoID: String,
    @PrimaryKey(autoGenerate = true)
    val id_DayData: Long,

    val isVoilation: Boolean? = false,

    val rideDesciption: String = "",

    val startLatitude: Double = 0.0,

    val startLocation: String = "",

    val startLongitude: Double = 0.0,

    val startOdometer: String = "",

    val startTime: String = "",

    val startTimeString: String = "",

    val date: String = ""
) : Parcelable