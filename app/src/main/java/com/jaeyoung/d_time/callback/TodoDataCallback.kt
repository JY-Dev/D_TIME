package com.jaeyoung.d_time.callback

import com.jaeyoung.d_time.room.TodoData

interface TodoDataCallback {
    fun finish(todoData : MutableList<TodoData>)
}