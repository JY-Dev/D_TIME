package com.jaeyoung.d_time.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TodoData")
data class TodoData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val date: String
)