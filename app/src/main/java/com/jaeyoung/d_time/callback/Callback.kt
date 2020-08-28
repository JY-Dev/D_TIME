package com.jaeyoung.d_time.callback

import com.jaeyoung.d_time.customView.TimeScheduleView
import com.jaeyoung.d_time.room.diary.DiaryData
import com.jaeyoung.d_time.room.diary.bookmark.BookMark
import com.jaeyoung.d_time.room.diary.bookmark.BookMarkData
import com.jaeyoung.d_time.room.timetable.TimeTableData
import com.jaeyoung.d_time.room.todo.TodoData


interface TodoDBGetCallback {
    fun finish(todoData: MutableList<TodoData>)
}

interface DBChangeCallback {
    fun changed()
}

interface DiaryDbGetCallback{
    fun finish(diaryData : MutableList<DiaryData>)
}

interface BookMarkDbAllGetCallback{
    fun finish(bookMarkData : MutableList<BookMarkData>)
}

interface BookMarkDataDbGetCallback{
    fun finish(bookMarkData : BookMarkData)
}

interface BookMarkDbGetCallback{
    fun finish(bookMarkData : MutableList<BookMark>)
}

interface DiaryModifyDbGetCallback{
    fun finish(diaryData : DiaryData)
}

interface TimeScheduleSelCallback{
    fun select(id: Long)
}

interface TimeTableDbGetCallback{
    fun finish(timeTableData: TimeTableData)
}

interface TimeTableDbAllGetCallback{
    fun finish(timeTableDataList: MutableList<TimeTableData>)
}