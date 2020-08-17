package com.jaeyoung.d_time.room.diary.bookmark

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookMarkData(@PrimaryKey val id : Long, val bookMark:String)

@Entity
data class BookMark(@PrimaryKey val bookMark: String)