package com.jaeyoung.d_time.utils.dataprocess

import android.content.Context
import com.jaeyoung.d_time.callback.BookMarkDbAllGetCallback
import com.jaeyoung.d_time.callback.BookMarkDbGetCallback
import com.jaeyoung.d_time.callback.DBChangeCallback
import com.jaeyoung.d_time.room.diary.bookmark.BookMark
import com.jaeyoung.d_time.room.diary.bookmark.BookMarkDB
import com.jaeyoung.d_time.room.diary.bookmark.BookMarkData
import com.jaeyoung.d_time.room.diary.bookmark.BookMarkDataDB
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class DataProcessBookMark(context: Context) {
    private val mContext = context

    fun insertBookMark(
        bookMarkData: BookMark,
        dbChangeCallback: DBChangeCallback
    ) {
        val a = Observable.just(BookMarkDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getBookMarkDao()?.insertBookMark(bookMarkData)
                dbChangeCallback.changed()
            }
    }

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

    fun getBookMarkData(bookMarkDbGetCallback: BookMarkDbGetCallback) {
        val a = Observable.just(BookMarkDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getBookMarkDao()?.getBookMark()
                    ?.let { data -> bookMarkDbGetCallback.finish(data) }
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

    fun getIdBookMarkData(id:Long,bookMarkDbAllGetCallback: BookMarkDbAllGetCallback) {
        val a = Observable.just(BookMarkDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getBookMarkDao()?.getBookMarkIdData(id)
                    ?.let { data -> bookMarkDbAllGetCallback.finish(data) }
            }
    }

    fun getBookMarkData(bookMark:String,bookMarkDbAllGetCallback: BookMarkDbAllGetCallback) {
        val a = Observable.just(BookMarkDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getBookMarkDao()?.getBookMarkData(bookMark)
                    ?.let { data -> bookMarkDbAllGetCallback.finish(data) }
            }
    }

    fun deleteBookMark(bookMark: String, dbChangeCallback: DBChangeCallback) {
        val a = Observable.just(BookMarkDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getBookMarkDao()?.deleteBookMark(bookMark)
                dbChangeCallback.changed()
            }
    }

    fun deleteBookMarkData(id:Long,bookMark: String , dbChangeCallback: DBChangeCallback) {
        val a = Observable.just(BookMarkDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getBookMarkDao()?.deleteBookMarkData(id,bookMark)
                dbChangeCallback.changed()
            }
    }

    fun deleteIdBookMarkData(id:Long) {
        val a = Observable.just(BookMarkDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getBookMarkDao()?.deleteIdBookMarkData(id)
            }
    }

}