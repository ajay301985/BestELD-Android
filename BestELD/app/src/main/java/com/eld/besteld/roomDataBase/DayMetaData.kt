package com.eld.besteld.roomDataBase

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "DayMetaData")
data class DayMetaData(
    @PrimaryKey(autoGenerate = false)
    val id_DayMetaData: Long,

    @SerializedName("day")
    val day_meta: String,

    val id: String,

    val driverId: String,

    val dlNumber: String
//    val dayDataArray: DayData? = null,

//    val inspectionArray: Inspection? = null
  /*  @PrimaryKey
@SerializedName("dlNumber")
val dlNumber: String*/
) : Parcelable