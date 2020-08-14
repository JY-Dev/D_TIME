package com.jaeyoung.d_time.room.todo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TodoDao {

    /**
     * Get All TodoData
     */
    @Query("SELECT * FROM TodoData")
    fun getTodoAllData() : MutableList<TodoData>

    /**
     * Get Express TodoData
     */
    @Query("SELECT * FROM TodoData WHERE `date` =:date")
    fun getTodoData(date:String) : MutableList<TodoData>

    /**
     * Update Certain TodoData
     */
    @Query("UPDATE TodoData SET isClear =:isChecked WHERE `id` =:primaryKey")
    fun updateTodoData(isChecked:Boolean,primaryKey: Long)

    /**
     * Insert TodoData
     */
    @Insert
    fun insert(vararg todoData: TodoData)

    /**
     * Delete TodoData
     */
    @Query("DELETE FROM TodoData WHERE `id` =:primaryKey")
    fun deleteTodoData(primaryKey: Long)
}