package com.jaeyoung.d_time.room.diary

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * TodoDB
 */
@Database(entities = [DiaryData::class],version = 1)
abstract class DiaryDataDB : RoomDatabase() {
    abstract fun getDiaryDao() : DiaryDao
    companion object{
        private var INSTANCE : DiaryDataDB? = null

        fun getInstance(context: Context) : DiaryDataDB?{
            if(INSTANCE == null){
                synchronized(DiaryDataDB::class){
                    INSTANCE = Room.databaseBuilder(context,
                        DiaryDataDB::class.java,"diarydata.db").build()
                }
            }

            return INSTANCE
        }
    }
}