package com.jaeyoung.d_time.viewModel.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaeyoung.d_time.callback.BookMarkDbAllGetCallback
import com.jaeyoung.d_time.callback.BookMarkDbGetCallback
import com.jaeyoung.d_time.callback.DBChangeCallback
import com.jaeyoung.d_time.room.diary.bookmark.BookMark
import com.jaeyoung.d_time.room.diary.bookmark.BookMarkData
import com.jaeyoung.d_time.utils.dataprocess.DataProcessBookMark
import org.koin.core.KoinComponent
import org.koin.core.inject

class BookMarkViewModel : ViewModel(), KoinComponent {
    val dataProcessBookMark: DataProcessBookMark by inject()
    val bookMarkListData = MutableLiveData<MutableList<BookMarkData>>()
    val bookMarkList: LiveData<MutableList<BookMarkData>> get() = bookMarkListData
    val bookMarkTitleData = MutableLiveData<MutableList<BookMark>>()
    val bookMarkTitle: LiveData<MutableList<BookMark>> get() = bookMarkTitleData
    val statusData = MutableLiveData<String>()
    val status: LiveData<String> get() = statusData

    fun getAllData() {
        dataProcessBookMark.getAllBookMarkData(object : BookMarkDbAllGetCallback {
            override fun finish(bookMarkData: MutableList<BookMarkData>) {
                bookMarkListData.postValue(bookMarkData)
            }
        })
    }

    fun getBookMarkData(bookMark: String) {
        dataProcessBookMark.getBookMarkData(bookMark, object : BookMarkDbAllGetCallback {
            override fun finish(bookMarkData: MutableList<BookMarkData>) {
                bookMarkListData.postValue(bookMarkData)
            }
        })
    }

    fun getIdData(id: Long) {
        dataProcessBookMark.getIdBookMarkData(id, object : BookMarkDbAllGetCallback {
            override fun finish(bookMarkData: MutableList<BookMarkData>) {
                bookMarkListData.postValue(bookMarkData)
            }
        })
    }

    fun getBookMark() {
        dataProcessBookMark.getBookMarkData(object : BookMarkDbGetCallback {
            override fun finish(bookMarkData: MutableList<BookMark>) {
                bookMarkTitleData.postValue(bookMarkData)
            }
        })
    }

    fun insertBookmarkData(bookMark: BookMarkData) {
        dataProcessBookMark.insertBookMarkData(bookMark, object : DBChangeCallback {
            override fun changed() {
                statusData.postValue("change")
            }
        })
    }

    fun inserBookMark(bookMark: BookMark) {
        dataProcessBookMark.insertBookMark(bookMark, object : DBChangeCallback {
            override fun changed() {
                statusData.postValue("change")
            }
        })
    }

    fun deleteBookMark(bookMark: String) {
        dataProcessBookMark.deleteBookMark(bookMark, object : DBChangeCallback {
            override fun changed() {
                statusData.postValue("change")
            }
        })
    }

    fun deleteBookMarkData(id:Long,bookMark: String) {
        dataProcessBookMark.deleteBookMarkData(id,bookMark, object : DBChangeCallback{
            override fun changed() {
                statusData.postValue("change")
            }

        })
    }

    fun deleteIdBookMarkData(id:Long) {
        dataProcessBookMark.deleteIdBookMarkData(id)
    }
}