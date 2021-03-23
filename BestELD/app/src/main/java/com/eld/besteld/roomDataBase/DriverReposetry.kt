package com.eld.besteld.roomDataBase

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.eld.besteld.utils.TimeUtility
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*


class DriverReposetry(private val driverDao: insertDriverInformationDao, private val eldProfileDao: EldProfileDao) {

    val allInfromation: LiveData<List<DayData>> = driverDao.getDayData()

    fun insertDayData(dayData:DayData) {
        driverDao.insertDayData(dayData)
    }

    fun insertDriverIno(driverInformation:DriverInformation) {
        driverDao.insertDriverInformation(driverInformation)
    }

    fun insertEldProfile(eld: Eld){
        eldProfileDao.insertEldInfromation(eld)
    }

    //TODO: Add a method to save day data
    //@RequiresApi(Build.VERSION_CODES.O)
    /*
    fun insertDayDataForDayMetaData(dayData:DayData, dayMetaData: Date, dlNumber: String) {
        //if meta data is avaiable for that day
        //convert date to start of the day
        //val startOfDay: LocalDateTime = LocalDateTime.of(dayMetaData, LocalTime.MIDNIGHT)


        val currentTime = TimeUtility.startOfTheDay(dayMetaData)

        val metaDataList = driverDao.getUsersWithPlaylists("6000909")
        if (metaDataList.isEmpty()) {
            val currentDayMetaData = DayMetaData(currentTime.toString(),null,"xyz", dlNumber)
            driverDao.insertDayMetaData(currentDayMetaData)
            var currentDayData = dayData
            currentDayData.day = currentTime.toString()
            driverDao.insertDayData(currentDayData)
            val metaDataList = driverDao.getUsersWithPlaylists(currentTime.toString())
            print("get some data")
        }else {

        }*/

        /*
        val now = DateTime()
        val firstMomentOfToday: DateTime = dayMetaData.withTimeAtStartOfDay()

        val now = LocalDateTime.now() // 2015-11-19T19:42:19.224
        dayMetaData.with(LocalTime.MIN) // 2015-11-19T00:00

        now.with(LocalTime.MIDNIGHT) // 2015-11-19T00:00

        final LocalDateTime startOfDay = LocalDateTime.of(dateTime, LocalTime.MIN);

// end of a day
// end of a day
        now.with(LocalTime.MAX) // 2015-11-19T23:59:59.999999999
 */
        //
        //final LocalDateTime startOfDay       = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        //final Date          startOfDayAsDate = Date.from(startOfDay.toInstant(ZoneOffset.UTC));
   // }
}