package com.jaeyoung.d_time.utils.dataprocess

import android.content.Context
import com.jaeyoung.d_time.callback.DBChangeCallback
import com.jaeyoung.d_time.callback.DiaryDbGetCallback
import com.jaeyoung.d_time.callback.DiaryModifyDbGetCallback
import com.jaeyoung.d_time.room.diary.DiaryData
import com.jaeyoung.d_time.room.diary.DiaryDataDB
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class DataProcessDiary(context: Context) {
    private val mContext = context
    /**
     * Insert TodoData
     * TodoData(PrimaryKey:Long, Date : String, Title: String, isClear : Boolean)
     */
    fun insertDiaryData(
        diaryData: DiaryData,
        dbChangeCallback : DBChangeCallback
    ) {
        val a = Observable.just(DiaryDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getDiaryDao()?.insert(diaryData)
                dbChangeCallback.changed()
            }
    }

    /**
     * Get DiaryData
     * DiaryData(PrimaryKey:Long, Date : String, Title: String, isClear : Boolean)
     */
    fun getDiaryData(date: String,diaryDbGetCallback: DiaryDbGetCallback) {
        val a = Observable.just(DiaryDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getDiaryDao()?.getDiaryData(date)?.let { data -> diaryDbGetCallback.finish(data) }
            }
    }

    fun getModifyDiaryData( primaryKey: Long,diaryModifyDbGetCallback: DiaryModifyDbGetCallback) {
        val a = Observable.just(DiaryDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getDiaryDao()?.getDiaryOneData(primaryKey)?.let { data -> diaryModifyDbGetCallback.finish(data) }
            }
    }

    fun getAllDiaryData(diaryDbGetCallback: DiaryDbGetCallback) {
        val a = Observable.just(DiaryDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getDiaryDao()?.getDiaryAllData()?.let { data -> diaryDbGetCallback.finish(data) }
            }
    }

    /**
     * Update TodoData
     * TodoData(PrimaryKey:Long, Date : String, Title: String, isClear : Boolean)
     */
    fun updateDiaryData(diaryData: DiaryData , dbChangeCallback: DBChangeCallback) {
        val a = Observable.just(DiaryDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getDiaryDao()?.updateDiaryData(diaryData)
                dbChangeCallback.changed()
            }
    }


    /**
     * Delete TodoData
     * TodoData(PrimaryKey:Long, Date : String, Title: String, isClear : Boolean)
     */
    fun deleteDiaryData(primaryKey: Long, dbChangeCallback : DBChangeCallback) {
        val a = Observable.just(DiaryDataDB.getInstance(mContext))
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                db?.getDiaryDao()?.deleteDiaryData(primaryKey)
                dbChangeCallback.changed()
            }
    }

}