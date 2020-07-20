package com.jaeyoung.d_time.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TodoDao {
    @Query("SELECT * FROM TodoData WHERE `date` =:date")
    fun getTodoData(date : String) : List<TimeTableData>

    @Query("DELETE FROM TodoData WHERE `id` =:primaryKey")
    fun deleteTodoData(primaryKey: Int)
}