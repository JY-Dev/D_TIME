package com.jaeyoung.d_time.viewModel.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaeyoung.d_time.callback.BookMarkDbAllGetCallback
import com.jaeyoung.d_time.callback.DBChangeCallback
import com.jaeyoung.d_time.room.diary.bookmark.BookMarkData
import com.jaeyoung.d_time.utils.dataprocess.DataProcessBookMark
import org.koin.core.KoinComponent
import org.koin.core.inject

class BookMarkViewModel :ViewModel(), KoinComponent{
    val dataProcessBookMark : DataProcessBookMark by inject()
    val bookMarkListData = MutableLiveData<MutableList<BookMarkData>>()
    val bookMarkList : LiveData<MutableList<BookMarkData>> get() = bookMarkListData

    fun getAllData(){
        dataProcessBookMark.getAllBookMarkData(object : BookMarkDbAllGetCallback{
            override fun finish(bookMarkData: MutableList<BookMarkData>) {
                bookMarkListData.postValue(bookMarkData)
            }
        })
    }

    fun insertData(bookMarkData: BookMarkData){
        dataProcessBookMark.insertBookMarkData(bookMarkData,object : DBChangeCallback{
            override fun changed() {

            }
        })
    }

    fun updateData(bookMarkData: BookMarkData){
        dataProcessBookMark.updateBookMarkData(bookMarkData,object : DBChangeCallback{
            override fun changed() {

            }
        })
    }

    fun deleteData(bookMark: String){
        dataProcessBookMark.deleteBookMarkData(bookMark, object : DBChangeCallback{
            override fun changed() {

            }

        })
    }
}