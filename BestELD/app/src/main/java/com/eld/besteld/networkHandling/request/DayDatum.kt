package com.eld.besteld.networkHandling.request

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity
data class DayDatum(
    private var dayDataId: Int? = null,

    private var dlnumber: String? = null,

    private var dutystatus: String? = null,

    private var startlocation: String? = null,

    private var endlocation: String? = null,

    private var startlocationlatitude: String? = null,

    private var startlocationlongitude: String? = null,

    private var endlocationlatitude: String? = null,

    private var endlocationlongitude: String? = null,

    private var startodometer: Int? = null,

    private var starttime: String? = null,

    private var endtime: String? = null,

    private var endodometer: Int? = null,

    private var ridedescription: String? = null
) : Parcelable