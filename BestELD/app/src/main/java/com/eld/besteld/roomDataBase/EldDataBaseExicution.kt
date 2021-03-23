package com.eld.besteld.roomDataBase
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [DayData::class, DayMetaData::class, DriverInformation::class, Eld::class, Inspection::class], version = 1 ,exportSchema = false)

abstract class EldDataBaseExicution : RoomDatabase(){
    abstract fun getDriverDao() : insertDriverInformationDao
    abstract fun dayDataDao(): DayDataDao
    abstract fun dayMetaDataDao(): DayMetaDataDao
    abstract fun getEldProfileDao() :  EldProfileDao
/*

    companion object {
        var INSTANCE: EldDataBaseExicution? = null

        fun getAppDataBase(context: Context): EldDataBaseExicution? {
            if (INSTANCE == null){
                synchronized(EldDataBaseExicution::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, EldDataBaseExicution::class.java, "myDB").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }*/

    companion object{

        @Volatile
        private var eldDataBaseExicutionInstance : EldDataBaseExicution? = null
        private val LOCK = Any()

        operator fun invoke(context : Context) =
            eldDataBaseExicutionInstance ?: synchronized(LOCK){

                eldDataBaseExicutionInstance ?: bindDataBase(context).also{
                eldDataBaseExicutionInstance = it
            }

        }

        private fun bindDataBase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
                    EldDataBaseExicution::class.java,
            "EldDataBaseExicution"

        ).build()
    }

   // abstract val driverInformation : insertDriverInformationDao?



}