package com.jaeyoung.d_time.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TodoData")
data class TimeTableData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val date: String,
    val startTime: String,
    val endTime: String
)