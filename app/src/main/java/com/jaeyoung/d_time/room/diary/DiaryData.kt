package com.jaeyoung.d_time.room.diary

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DiaryData(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val date: String,
    val title: String,
    val subTitle: String,
    val imgUriArray: String,
    val weather: Int,
    val emotion: Int
)