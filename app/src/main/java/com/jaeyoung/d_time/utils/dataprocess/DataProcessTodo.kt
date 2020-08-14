package com.jaeyoung.d_time.utils.dataprocess

import android.content.Context
import com.jaeyoung.d_time.callback.DBChangeCallback
import com.jaeyoung.d_time.callback.TodoDBGetCallback
import com.jaeyoung.d_time.room.todo.TodoData
import com.jaeyoung.d_time.room.todo.TodoDataDB
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Room DataProcessing
 */
class DataProcessTodo(context: Context) {
    private val mContext = context

    /**
     * Insert TodoData
     * TodoData(PrimaryKey:Long, Date : String, Title: String, isClear : Boolean)
     */
    fun insertTodoData(todoData: TodoData, DBChangeCallback: DBChangeCallback) {
        val a = Observable.just(TodoDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getTodoDao()?.insert(todoData)
                DBChangeCallback.changed()
            }
    }

    /**
     * Get TodoData
     * TodoData(PrimaryKey:Long, Date : String, Title: String, isClear : Boolean)
     */
    fun getTodoData(todoDBGetCallback: TodoDBGetCallback, date: String) {
        val a = Observable.just(TodoDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getTodoDao()?.getTodoData(date)?.let { data -> todoDBGetCallback.finish(data) }
            }
    }


    /**
     * Update TodoData
     * TodoData(PrimaryKey:Long, Date : String, Title: String, isClear : Boolean)
     */
    fun updateTodoData(isChecked: Boolean, primaryKey: Long) {
        val a = Observable.just(TodoDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getTodoDao()?.updateTodoData(isChecked, primaryKey)
            }
    }


    /**
     * Delete TodoData
     * TodoData(PrimaryKey:Long, Date : String, Title: String, isClear : Boolean)
     */
    fun deleteTodoData(primaryKey: Long) {
        val a = Observable.just(TodoDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getTodoDao()?.deleteTodoData(primaryKey)
            }
    }
}