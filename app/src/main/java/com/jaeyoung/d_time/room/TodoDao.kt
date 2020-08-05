package com.jaeyoung.d_time.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoDao {
    @Query("SELECT * FROM TodoData")
    fun getTodoAllData() : MutableList<TodoData>

    //이미 저장된 항목이 있을 경우 데이터를 덮어쓴다
    @Insert
    fun insert(vararg todoData: TodoData)

    @Query("DELETE FROM TodoData WHERE `id` =:primaryKey")
    fun deleteTodoData(primaryKey: Long)
}