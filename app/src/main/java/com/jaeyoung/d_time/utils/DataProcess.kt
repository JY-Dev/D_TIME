package com.jaeyoung.d_time.utils

import android.content.Context
import com.jaeyoung.d_time.TodoDataCallback
import com.jaeyoung.d_time.room.TodoData
import com.jaeyoung.d_time.room.TodoDataDB
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DataProcess(context: Context) {
    private val mContext = context
    fun insertTodoData( date: String, title: String) {
        val a = Observable.just(TodoData(System.currentTimeMillis(),date,title))
            .subscribeOn(Schedulers.io())
            .subscribe {
                TodoDataDB.getInstance(mContext)?.getTodoDao()?.insert(it)
            }
    }

    fun getTodoData(todoDataCallback: TodoDataCallback){
        val a = Observable.just(TodoDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe{
                it?.getTodoDao()?.getTodoAllData()?.let { it1 -> todoDataCallback.finish(it1) }
            }
    }

    fun deleteTodoData(primaryKey: Int){
        TodoDataDB.getInstance(mContext)?.getTodoDao()?.deleteTodoData(primaryKey)
    }
}