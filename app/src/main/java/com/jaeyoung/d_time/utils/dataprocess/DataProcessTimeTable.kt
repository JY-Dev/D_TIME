package com.jaeyoung.d_time.utils.dataprocess

import android.content.Context
import com.jaeyoung.d_time.callback.DBChangeCallback
import com.jaeyoung.d_time.callback.TimeTableDbAllGetCallback
import com.jaeyoung.d_time.callback.TimeTableDbGetCallback
import com.jaeyoung.d_time.room.diary.bookmark.BookMark
import com.jaeyoung.d_time.room.diary.bookmark.BookMarkDB
import com.jaeyoung.d_time.room.timetable.TimeTableData
import com.jaeyoung.d_time.room.timetable.TimeTableDataDB
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class DataProcessTimeTable(context: Context) {
    private val mContext = context
    private val timeTableDataDB = TimeTableDataDB.getInstance(mContext)
    fun getIdData(id: Long, timeTableDbGetCallback: TimeTableDbGetCallback) {
        val a = Observable.just(timeTableDataDB)
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getTimeTableDao()?.getOneTimeTableData(id)
                    ?.let { data -> timeTableDbGetCallback.finish(data) }
            }
    }

    fun getAllData(date: String, timeTableDbAllGetCallback: TimeTableDbAllGetCallback) {
        val a = Observable.just(timeTableDataDB)
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getTimeTableDao()?.getTimeTableData(date)
                    ?.let { data -> timeTableDbAllGetCallback.finish(data) }
            }
    }

    fun insertData(timeTableData: TimeTableData,dbChangeCallback: DBChangeCallback) {
        val a = Observable.just(timeTableDataDB)
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getTimeTableDao()?.insertTimeTableData(timeTableData)
                dbChangeCallback.changed()
            }
    }

    fun updateData(timeTableData: TimeTableData,dbChangeCallback: DBChangeCallback) {
        val a = Observable.just(timeTableDataDB)
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getTimeTableDao()?.updateTimeTableData(timeTableData)
                dbChangeCallback.changed()
            }
    }

    fun deleteData(id: Long,dbChangeCallback: DBChangeCallback) {
        val a = Observable.just(timeTableDataDB)
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getTimeTableDao()?.deleteTimeTableData(id)
                dbChangeCallback.changed()
            }
    }
}