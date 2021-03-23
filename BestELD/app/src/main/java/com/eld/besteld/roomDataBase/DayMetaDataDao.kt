package com.eld.besteld.roomDataBase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface  DayMetaDataDao {

    @Query("select * from DayMetaData")
    fun getDayMetaData(): LiveData<List<DayMetaData>>

    @Transaction
    //@Query("SELECT * FROM DayMetaData where day = :inDay")
    @Query("SELECT * FROM DayMetaData")
    fun getUsersWithPlaylists(): List<DayMetaDataWithDayDataList>

    @Transaction
    //@Query("SELECT * FROM DayMetaData where day = :inDay")
    @Query("SELECT * FROM DayMetaData where id_DayMetaData = :dayTimeInterval")
    fun getCurrentDayMetaData(dayTimeInterval: Long): List<DayMetaDataWithDayDataList>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDayMetaData(metaDta: DayMetaData)

}