package com.eld.besteld.roomDataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy


@Dao
interface EldProfileDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEldInfromation(eldProfile: Eld)
}