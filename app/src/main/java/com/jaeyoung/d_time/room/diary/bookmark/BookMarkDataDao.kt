package com.jaeyoung.d_time.room.diary.bookmark

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookMarkDataDao {
    @Insert
    fun insertBookMarkData(bookMarkData: BookMarkData)

    @Query("SELECT * FROM BookMarkData WHERE `bookMark` =:bookMark")
    fun getBookMarkData(bookMark:String) : BookMarkData

    @Query("SELECT * FROM BookMarkData")
    fun getBookMarkAllData() : MutableList<BookMarkData>

}