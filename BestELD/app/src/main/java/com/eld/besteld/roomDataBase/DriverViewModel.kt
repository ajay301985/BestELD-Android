package com.eld.besteld.roomDataBase

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class DriverViewModel(application: Application) : AndroidViewModel(application) {

    private val noteReposetry:DriverReposetry
    val dayDaya : LiveData<List<DayData>>

    init{
        val dao = EldDataBaseExicution.invoke(application)?.getDriverDao()
        val eldDao = EldDataBaseExicution.invoke(application)?.getEldProfileDao()
        noteReposetry = DriverReposetry(dao,eldDao)
        dayDaya = noteReposetry.allInfromation
    }

    /*
    fun insertDayData(dayData:DayData) = viewModelScope.launch( Dispatchers.IO ) {
        noteReposetry.insertDayData(dayData)
    }

    @RequiresApi(Build.VERSION_CODES.O)
*/
    fun insertDriverInfromation(driverInformation: DriverInformation) = viewModelScope.launch (Dispatchers.IO){
        noteReposetry.insertDriverIno(driverInformation)
    }

    fun insertEldProfileInformation(eld:Eld) = viewModelScope.launch (Dispatchers.IO ){

        noteReposetry.insertEldProfile(eld)
    }

}