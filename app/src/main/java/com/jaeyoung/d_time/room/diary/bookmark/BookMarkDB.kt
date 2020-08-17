package com.jaeyoung.d_time.room.diary.bookmark

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * TodoDB
 */
@Database(entities = [BookMark::class],version = 1)
abstract class BookMarkDB : RoomDatabase() {
    abstract fun getBookMarkDao() : BookMarkDao
    companion object{
        private var INSTANCE : BookMarkDB? = null

        fun getInstance(context: Context) : BookMarkDB?{
            if(INSTANCE == null){
                synchronized(BookMarkDB::class){
                    INSTANCE = Room.databaseBuilder(context,
                        BookMarkDB::class.java,"bookmark.db").build()
                }
            }
            return INSTANCE
        }
    }
}