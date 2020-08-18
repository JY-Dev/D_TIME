package com.jaeyoung.d_time.room.diary.bookmark

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookMarkDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookMarkData(bookMarkData: BookMarkData)

    @Query("SELECT * FROM BookMarkData WHERE `bookMark` =:bookMark")
    fun getBookMarkData(bookMark:String) : MutableList<BookMarkData>

    @Query("SELECT * FROM BookMarkData WHERE `id` =:id")
    fun getBookMarkIdData(id:Long) : MutableList<BookMarkData>

    @Query("SELECT * FROM BookMarkData")
    fun getBookMarkAllData() : MutableList<BookMarkData>

    @Query("DELETE FROM BookMarkData WHERE `bookMark` =:bookMark AND `id` =:id")
    fun deleteBookMarkData(id: Long,bookMark: String)

    @Query("DELETE FROM BookMarkData WHERE `id` =:id")
    fun deleteIdBookMarkData(id: Long)

}