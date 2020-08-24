package com.jaeyoung.d_time.room.timetable

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * TodoDB
 */
@Database(entities = [TimeTableData::class],version = 1)
abstract class TimeTableDataDB : RoomDatabase() {
    abstract fun getTimeTableDao() : TimeTableDao
    companion object{
        private var INSTANCE : TimeTableDataDB? = null

        fun getInstance(context: Context) : TimeTableDataDB?{
            if(INSTANCE == null){
                synchronized(TimeTableDataDB::class){
                    INSTANCE = Room.databaseBuilder(context,
                        TimeTableDataDB::class.java,"timetabledata.db").build()
                }
            }

            return INSTANCE
        }
    }
}