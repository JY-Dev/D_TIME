package com.jaeyoung.d_time.utils.dataprocess

import android.content.Context
import com.jaeyoung.d_time.callback.BookMarkDbAllGetCallback
import com.jaeyoung.d_time.callback.BookMarkDbGetCallback
import com.jaeyoung.d_time.callback.DBChangeCallback
import com.jaeyoung.d_time.room.diary.bookmark.BookMarkData
import com.jaeyoung.d_time.room.diary.bookmark.BookMarkDataDB
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class DataProcessBookMark(context: Context) {
    private val mContext = context

    fun insertBookMarkData(
        bookMarkData: BookMarkData,
        dbChangeCallback: DBChangeCallback
    ) {
        val a = Observable.just(BookMarkDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getBookMarkDao()?.insertBookMarkData(bookMarkData)
                dbChangeCallback.changed()
            }
    }

    fun getBookMarkData(bookMark: String, bookMarkDbGetCallback: BookMarkDbGetCallback) {
        val a = Observable.just(BookMarkDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getBookMarkDao()?.getBookMarkData(bookMark)
                    ?.let { data -> bookMarkDbGetCallback.finish(data) }
            }
    }

    fun updateBookMarkData(
        bookMarkData: BookMarkData,
        dbChangeCallback: DBChangeCallback
    ) {
        val a = Observable.just(BookMarkDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getBookMarkDao()?.updateBookMarkData(bookMarkData)
                dbChangeCallback.changed()
            }
    }

    fun getAllBookMarkData(bookMarkDbAllGetCallback: BookMarkDbAllGetCallback) {
        val a = Observable.just(BookMarkDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getBookMarkDao()?.getBookMarkAllData()
                    ?.let { data -> bookMarkDbAllGetCallback.finish(data) }
            }
    }


    fun deleteBookMarkData(bookMark: String, dbChangeCallback: DBChangeCallback) {
        val a = Observable.just(BookMarkDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getBookMarkDao()?.deleteBookMarkData(bookMark)
                dbChangeCallback.changed()
            }
    }

}