package com.jaeyoung.d_time.utils

import android.content.Context
import com.jaeyoung.d_time.callback.TodoDataCallback
import com.jaeyoung.d_time.room.TodoData
import com.jaeyoung.d_time.room.TodoDataDB
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class DataProcess(context: Context) {
    private val mContext = context
    fun insertTodoData( date: String, title: String,todoDataCallback: TodoDataCallback) {
        val a = Observable.just(TodoDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db->
                db?.getTodoDao()?.insert(TodoData(System.currentTimeMillis(),date,title))
                getTodoData(todoDataCallback)
            }
    }

    fun getTodoData(todoDataCallback: TodoDataCallback){
        val a = Observable.just(TodoDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe{db ->
                db?.getTodoDao()?.getTodoAllData()?.let { it1 -> todoDataCallback.finish(it1) }
            }
    }

    fun deleteTodoData(primaryKey: Long,todoDataCallback: TodoDataCallback){
        val a = Observable.just(TodoDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getTodoDao()?.deleteTodoData(primaryKey)
            }
        getTodoData(todoDataCallback)
    }
}