package com.eld.besteld.roomDataBase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DayDataDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDayData(dayData:DayData)

    @Query("select * from DayData")
    fun getDayData(): LiveData<List<DayData>>

    @Update
    fun updateDayData(dayData:DayData)
}