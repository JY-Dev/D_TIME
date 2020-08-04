package com.jaeyoung.d_time.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoData::class],version = 1)
abstract class TodoDataDB : RoomDatabase() {
    abstract fun getTodoDao() : TodoDao
    companion object{
        private var INSTANCE : TodoDataDB? = null

        fun getInstance(context: Context) : TodoDataDB?{
            if(INSTANCE == null){
                synchronized(TodoDataDB::class){
                    INSTANCE = Room.databaseBuilder(context,TodoDataDB::class.java,"tododata.db").build()
                }
            }

            return INSTANCE
        }
    }
}