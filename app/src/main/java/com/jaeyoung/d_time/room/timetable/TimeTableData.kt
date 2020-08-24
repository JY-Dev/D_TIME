package com.jaeyoung.d_time.room.timetable

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TimeTableData(
    @PrimaryKey val id: Long,
    val date: String,
    val event: String,
    val note: String,
    val color: String,
    val startHour: Int,
    val startMin : Int,
    val endHour : Int,
    val endMin : Int
)