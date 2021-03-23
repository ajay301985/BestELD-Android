package com.eld.besteld.roomDataBase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface insertDriverInformationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insertDriverInformation(driverInformation: DriverInformation)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDayData(dayData:DayData)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDayMetaData(inDayMetaData: DayMetaData)

    @Query("select * from DayData")
    fun getDayData(): LiveData<List<DayData>>

    @Query("select * from DayMetaData")
    fun getDayMetaData():LiveData<List<DayMetaData>>

}