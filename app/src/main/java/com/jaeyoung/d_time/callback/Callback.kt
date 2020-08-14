package com.jaeyoung.d_time.callback

import com.jaeyoung.d_time.room.diary.DiaryData
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

interface DiaryModifyDbGetCallback{
    fun finish(diaryData : DiaryData)
}