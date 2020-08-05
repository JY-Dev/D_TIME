package com.jaeyoung.d_time.utils

import android.content.Context
import com.jaeyoung.d_time.callback.TodoDataCallback
import com.jaeyoung.d_time.room.TodoData
import com.jaeyoung.d_time.room.TodoDataDB
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class DataProcess(context: Context) {
    private val mContext = context
    fun insertTodoData(date: String, title: String, todoDataCallback: TodoDataCallback) {
        val a = Observable.just(TodoDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getTodoDao()?.insert(TodoData(System.currentTimeMillis(), date, title, false))
                getTodoData(todoDataCallback, date)
            }
    }

    fun getTodoData(todoDataCallback: TodoDataCallback, date: String) {
        val a = Observable.just(TodoDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getTodoDao()?.getTodoData(date)?.let { it1 -> todoDataCallback.finish(it1) }
            }
    }

    fun updateTodoData(isChecked: Boolean, primaryKey: Long) {
        val a = Observable.just(TodoDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getTodoDao()?.updateTodoData(isChecked, primaryKey)
            }
    }

    fun deleteTodoData(date: String, primaryKey: Long, todoDataCallback: TodoDataCallback) {
        val a = Observable.just(TodoDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getTodoDao()?.deleteTodoData(primaryKey)
            }
        getTodoData(todoDataCallback, date)
    }
}