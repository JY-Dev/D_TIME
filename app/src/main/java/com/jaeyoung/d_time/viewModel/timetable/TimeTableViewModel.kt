package com.jaeyoung.d_time.viewModel.timetable

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaeyoung.d_time.callback.DBChangeCallback
import com.jaeyoung.d_time.callback.TimeTableDbAllGetCallback
import com.jaeyoung.d_time.callback.TimeTableDbGetCallback
import com.jaeyoung.d_time.room.timetable.TimeTableData
import com.jaeyoung.d_time.utils.dataprocess.DataProcessTimeTable
import org.koin.core.KoinComponent
import org.koin.core.inject

class TimeTableViewModel : ViewModel(), KoinComponent {
    private val dataProcess: DataProcessTimeTable by inject()
    private val timeTableListData = MutableLiveData<MutableList<TimeTableData>>()
    val timeTableList: LiveData<MutableList<TimeTableData>> get() = timeTableListData
    private val timeTableData = MutableLiveData<TimeTableData>()
    val timeTable: LiveData<TimeTableData> get() = timeTableData
    private val statusData = MutableLiveData<String>()
    val status: LiveData<String> get() = statusData

    fun insertData(timeTableData: TimeTableData) {
        dataProcess.insertData(timeTableData, object : DBChangeCallback {
            override fun changed() {
                statusData.postValue("change")
            }
        })
    }

    fun updateData(timeTableData: TimeTableData) {
        dataProcess.updateData(timeTableData, object : DBChangeCallback {
            override fun changed() {
                statusData.postValue("change")
            }
        })
    }

    fun deleteData(id: Long) {
        dataProcess.deleteData(id, object : DBChangeCallback {
            override fun changed() {
                statusData.postValue("change")
            }
        })
    }

    fun getIdData(id: Long) {
        dataProcess.getIdData(id, object : TimeTableDbGetCallback {
            override fun finish(data: TimeTableData) {
                timeTableData.postValue(data)
            }

        })
    }

    fun getAllData(date: String) {
        dataProcess.getAllData(date, object : TimeTableDbAllGetCallback {
            override fun finish(data: MutableList<TimeTableData>) {
                timeTableListData.postValue(data)
            }
        })
    }
}