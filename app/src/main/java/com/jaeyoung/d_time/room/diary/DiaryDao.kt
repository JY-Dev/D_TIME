package com.jaeyoung.d_time.room.diary

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DiaryDao {
    /**
     * Get All DiaryData
     */
    @Query("SELECT * FROM DiaryData")
    fun getDiaryAllData() : MutableList<DiaryData>

    /**
     * Get Express DiaryData
     */
    @Query("SELECT * FROM DiaryData WHERE `date` =:date")
    fun getDiaryData(date:String) : MutableList<DiaryData>

    /**
     * Get One DiaryData
     */
    @Query("SELECT * FROM DiaryData WHERE `id` =:primaryKey")
    fun getDiaryOneData(primaryKey: Long) : DiaryData

    /**
     * Update Certain iaryData
     */
    @Update
    fun updateDiaryData(diaryData: DiaryData)

    /**
     * Insert iaryData
     */
    @Insert
    fun insert(vararg diaryData: DiaryData)

    /**
     * Delete iaryData
     */
    @Query("DELETE FROM DiaryData WHERE `id` =:primaryKey")
    fun deleteDiaryData(primaryKey: Long)
}