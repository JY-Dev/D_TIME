package com.jaeyoung.d_time.room.diary.bookmark

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * TodoDB
 */
@Database(entities = [BookMarkData::class],version = 1)
abstract class BookMarkDataDB : RoomDatabase() {
    abstract fun getBookMarkDao() : BookMarkDao
    companion object{
        private var INSTANCE : BookMarkDataDB? = null

        fun getInstance(context: Context) : BookMarkDataDB?{
            if(INSTANCE == null){
                synchronized(BookMarkDataDB::class){
                    INSTANCE = Room.databaseBuilder(context,
                        BookMarkDataDB::class.java,"bookmarkdata.db").build()
                }
            }
            return INSTANCE
        }
    }
}