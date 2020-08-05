package com.jaeyoung.d_time.viewModel

import android.widget.CheckBox
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaeyoung.d_time.callback.TodoDataCallback
import com.jaeyoung.d_time.room.TodoData
import com.jaeyoung.d_time.utils.DataProcess
import org.koin.core.KoinComponent
import org.koin.core.inject

class TodoViewModel : ViewModel() , KoinComponent {
    private val todoLiveData = MutableLiveData<MutableList<TodoData>>()
    val dataProcess : DataProcess by inject()
    val todoDataList : LiveData<MutableList<TodoData>> get() = todoLiveData

    fun getTodoData(date: String){
      dataProcess.getTodoData(object :
          TodoDataCallback {
          override fun finish(todoData: MutableList<TodoData>) {
              todoLiveData.postValue(todoData)
          }
      },date)
    }

    fun insertTodoData(date :String ,title :String ){
        dataProcess.insertTodoData(date, title,object :
            TodoDataCallback {
            override fun finish(todoData: MutableList<TodoData>) {
                todoLiveData.postValue(todoData)
            }
        })
    }

    fun deleteTodoData(primaryKey: Long,date: String){
        dataProcess.deleteTodoData(date,primaryKey, object  :
            TodoDataCallback {
            override fun finish(todoData: MutableList<TodoData>) {
                todoLiveData.postValue(todoData)
            }
        })
    }

    fun updateTodoData(isChecked: Boolean,primaryKey: Long){
        dataProcess.updateTodoData(isChecked,primaryKey)
    }
}