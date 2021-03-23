package com.eld.besteld.roomDataBase

import com.eld.besteld.utils.TimeUtility
import java.util.*

class LogDataRepository(private val dayDataDao: DayDataDao, private val dayMetaDataDao: DayMetaDataDao) {

    fun insertDayDataForDayMetaData(dayData:DayData, dayMetaData: Date, dlNumber: String) {
        dayDataDao.insertDayData(dayData)
    }

    fun updateDayData(dayData:DayData) {
        dayDataDao.updateDayData(dayData)
    }

    fun insertDayMetaData(metaData: DayMetaData) {
        dayMetaDataDao.insertDayMetaData(metaData)
    }

    fun getMetaDataList() {
        dayMetaDataDao.getUsersWithPlaylists()
    }
}