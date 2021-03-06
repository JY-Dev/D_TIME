package com.jaeyoung.d_time.viewModel.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

/**
 * CalendarViewModel
 */
class CalendarViewModel : ViewModel(){
    private val calLiveData = MutableLiveData<Calendar>()
    val calData : LiveData<Calendar> get() = calLiveData
    fun setCal(calendar: Calendar){
        calLiveData.postValue(calendar)
    }
}