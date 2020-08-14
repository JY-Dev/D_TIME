package com.jaeyoung.d_time.room.diary.bookmark

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jaeyoung.d_time.room.diary.DiaryData

@Dao
interface BookMarkDao {

    @Update
    fun updateBookMarkData(bookMarkData : BookMarkData)

    @Insert
    fun insertBookMarkData(bookMarkData: BookMarkData)

    @Query("DELETE FROM BookMarkData WHERE `bookMark` =:bookMark")
    fun deleteBookMarkData(bookMark: String)

    @Query("SELECT * FROM BookMarkData WHERE `bookMark` =:bookMark")
    fun getBookMarkData(bookMark:String) : BookMarkData

    @Query("SELECT * FROM BookMarkData")
    fun getBookMarkAllData() : MutableList<BookMarkData>
}