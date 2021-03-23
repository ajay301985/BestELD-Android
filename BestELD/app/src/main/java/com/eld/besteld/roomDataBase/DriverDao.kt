package com.eld.besteld.roomDataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface DriverDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDriverInformation(driverInformation: DriverInformation)

}