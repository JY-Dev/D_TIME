package com.jaeyoung.d_time.room.todo

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Todo Room Data
 * Date : TodoDate
 * Title : TodoList
 * isClear : Finished Todo?
 */
@Entity
data class TodoData(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val date: String,
    val title: String,
    val isClear : Boolean
)