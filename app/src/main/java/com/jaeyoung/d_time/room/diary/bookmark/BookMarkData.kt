package com.jaeyoung.d_time.room.diary.bookmark

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jaeyoung.d_time.room.diary.DiaryData

@Entity
data class BookMarkData(@PrimaryKey val bookMark:String , val diaryList : List<DiaryData>)