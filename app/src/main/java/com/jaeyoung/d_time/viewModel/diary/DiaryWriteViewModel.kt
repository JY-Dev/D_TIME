package com.jaeyoung.d_time.viewModel.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaeyoung.d_time.callback.DBChangeCallback
import com.jaeyoung.d_time.callback.DiaryModifyDbGetCallback
import com.jaeyoung.d_time.room.diary.DiaryData
import com.jaeyoung.d_time.utils.dataprocess.DataProcessDiary
import org.koin.core.KoinComponent
import org.koin.core.inject

class DiaryWriteViewModel : ViewModel() , KoinComponent {
    val dataProcess : DataProcessDiary by inject()
    private val statusData = MutableLiveData<String>()
    val status : LiveData<String> get() = statusData
    private val modifyData = MutableLiveData<DiaryData>()
    val modify : LiveData<DiaryData> get() = modifyData

    fun modifyGetData(primaryKey: Long){
        dataProcess.getModifyDiaryData(primaryKey,object : DiaryModifyDbGetCallback{
            override fun finish(diaryData: DiaryData) {
                modifyData.postValue(diaryData)
            }
        })
    }

    fun insertData(diaryData: DiaryData){
        dataProcess.insertDiaryData(diaryData,object : DBChangeCallback{
            override fun changed() {
                statusData.postValue("changed")
            }
        })
    }

    fun deleteData(primaryKey:Long){
        dataProcess.deleteDiaryData(primaryKey,object : DBChangeCallback{
            override fun changed() {
                statusData.postValue("changed")
            }
        })
    }

    fun updateData(diaryData: DiaryData){
        dataProcess.updateDiaryData(diaryData,object : DBChangeCallback{
            override fun changed() {
                statusData.postValue("changed")
            }
        })
    }
}