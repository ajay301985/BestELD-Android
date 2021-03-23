package com.eld.besteld.roomDataBase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class LogDataViewModel(application: Application) : AndroidViewModel(application) {

    private val logReposetry: LogDataRepository

    init{
        val dayDao = EldDataBaseExicution.invoke(application)?.dayDataDao()
        val dayMetaDao = EldDataBaseExicution.invoke(application)?.dayMetaDataDao()
        logReposetry = LogDataRepository(dayDataDao = dayDao!!,dayMetaDataDao = dayMetaDao!!)
    }

    fun insertDayDataForDayMetaData(dayData:DayData, dayMetaData: Date, dlNumber: String)=viewModelScope.launch (Dispatchers.IO){
        logReposetry.insertDayDataForDayMetaData(dayData, dayMetaData, dlNumber)
    }

    fun updateDayData(dayData:DayData)=viewModelScope.launch (Dispatchers.IO){    //I add Dispatchers.IO
        logReposetry.updateDayData(dayData)
    }

    fun insertDayMetaData(metaData: DayMetaData)=viewModelScope.launch (Dispatchers.IO){    //I add Dispatchers.IO
        logReposetry.insertDayMetaData(metaData)
    }

    fun getMetaDataList()=viewModelScope.launch (Dispatchers.IO){    //I add Dispatchers.IO
        logReposetry.getMetaDataList()
    }

/*    fun insertDayMetaData(metaData: DayMetaData) {
        logReposetry.insertDayMetaData(metaData)
    }*/

}