package com.jaeyoung.d_time.room.timetable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jaeyoung.d_time.room.diary.DiaryData

@Dao
interface TimeTableDao {

    @Query("SELECT * FROM TimeTableData WHERE `date` =:date")
    fun getTimeTableData(date : String) : MutableList<TimeTableData>

    @Query("SELECT * FROM TimeTableData WHERE `id` =:id")
    fun getOneTimeTableData(id : Long) : TimeTableData

    @Query("DELETE FROM TimeTableData WHERE `id` =:id")
    fun deleteTimeTableData(id: Long)

    @Update
    fun updateTimeTableData(timeTableData: TimeTableData)

    @Insert
    fun insertTimeTableData(timeTableData: TimeTableData)
}