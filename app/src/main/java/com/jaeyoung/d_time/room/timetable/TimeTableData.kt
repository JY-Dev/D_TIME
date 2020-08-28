package com.jaeyoung.d_time.room.timetable

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jaeyoung.d_time.customView.TimeScheduleView

@Entity
data class TimeTableData(
    @PrimaryKey val id: Long,
    val date: String,
    val event: String,
    val note: String,
    val color: String,
    val timeData : String
)