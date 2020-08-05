package com.jaeyoung.d_time.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoData(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val date: String,
    val title: String,
    val isClear : Boolean
)