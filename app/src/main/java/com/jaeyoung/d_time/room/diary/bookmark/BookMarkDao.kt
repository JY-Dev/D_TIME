package com.jaeyoung.d_time.room.diary.bookmark

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jaeyoung.d_time.room.diary.DiaryData

@Dao
interface BookMarkDao {

    @Insert
    fun insertBookMark(bookMark: BookMark)

    @Query("DELETE FROM BookMark WHERE `bookMark` =:bookMark")
    fun deleteBookMark(bookMark: String)

    @Query("SELECT * FROM BookMark")
    fun getBookMark() : MutableList<BookMark>

}