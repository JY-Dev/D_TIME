package com.jaeyoung.d_time.room.diary.bookmark

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.jaeyoung.d_time.room.diary.DiaryData

@Entity(primaryKeys = arrayOf("id","bookMark"))
data class BookMarkData(val id : Long, val bookMark:String)

@Entity
data class BookMark(@PrimaryKey val bookMark: String)
