package com.jaeyoung.d_time.viewModel.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaeyoung.d_time.callback.DBChangeCallback
import com.jaeyoung.d_time.callback.DiaryDbGetCallback
import com.jaeyoung.d_time.callback.DiaryModifyDbGetCallback
import com.jaeyoung.d_time.callback.TodoDBGetCallback
import com.jaeyoung.d_time.room.diary.DiaryData
import com.jaeyoung.d_time.room.todo.TodoData
import com.jaeyoung.d_time.utils.dataprocess.DataProcessBookMark
import com.jaeyoung.d_time.utils.dataprocess.DataProcessDiary
import org.koin.core.KoinComponent
import org.koin.core.inject

class DiaryViewModel : ViewModel(),KoinComponent {
    val dataProcess : DataProcessDiary by inject()
    val dataProcessBookMark: DataProcessBookMark by inject()
    private val diaryListData = MutableLiveData<MutableList<DiaryData>>()
    val diaryList : LiveData<MutableList<DiaryData>> get() = diaryListData
    private val diaryData = MutableLiveData<DiaryData>()
    val diary : LiveData<DiaryData> get() = diaryData
    private val statusData = MutableLiveData<String>()
    val status : LiveData<String> get() = statusData
    private val diaryAllListData = MutableLiveData<MutableList<DiaryData>>()
    val diaryAllList : LiveData<MutableList<DiaryData>> get() = diaryAllListData

    fun getDiaryData(date: String){
        dataProcess.getDiaryData(date,object :
            DiaryDbGetCallback {
            override fun finish(diaryData: MutableList<DiaryData>) {
                diaryListData.postValue(diaryData)
            }
        })
    }

    fun getAllDiaryData(){
        dataProcess.getAllDiaryData(object :
            DiaryDbGetCallback {
            override fun finish(diaryData: MutableList<DiaryData>) {
                diaryAllListData.postValue(diaryData)
            }
        })
    }

    fun getDiaryOneData(primaryKey: Long){
        dataProcess.getModifyDiaryData(primaryKey,object : DiaryModifyDbGetCallback {
            override fun finish(data: DiaryData) {
                diaryData.postValue(data)
            }
        })
    }

    fun deleteData(primaryKey:Long){
        dataProcess.deleteDiaryData(primaryKey,object : DBChangeCallback {
            override fun changed() {
                statusData.postValue("changed")
            }
        })
    }

    fun deleteIdBookMarkData(id:Long) {
        dataProcessBookMark.deleteIdBookMarkData(id)
    }
}