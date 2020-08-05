package com.jaeyoung.d_time.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoDao {
    @Query("SELECT * FROM TodoData")
    fun getTodoAllData() : MutableList<TodoData>

    @Query("SELECT * FROM TodoData WHERE `date` =:date")
    fun getTodoData(date:String) : MutableList<TodoData>

    @Query("UPDATE TodoData SET isClear =:isChecked WHERE `id` =:primaryKey")
    fun updateTodoData(isChecked:Boolean,primaryKey: Long)

    //이미 저장된 항목이 있을 경우 데이터를 덮어쓴다
    @Insert
    fun insert(vararg todoData: TodoData)

    @Query("DELETE FROM TodoData WHERE `id` =:primaryKey")
    fun deleteTodoData(primaryKey: Long)
}