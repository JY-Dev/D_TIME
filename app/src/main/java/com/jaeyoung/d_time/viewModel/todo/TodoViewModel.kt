package com.jaeyoung.d_time.viewModel.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaeyoung.d_time.callback.DBChangeCallback
import com.jaeyoung.d_time.callback.TodoDBGetCallback
import com.jaeyoung.d_time.room.todo.TodoData
import com.jaeyoung.d_time.utils.dataprocess.DataProcessTodo
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * TodoViewModel
 */
class TodoViewModel : ViewModel() , KoinComponent {
    private val todoLiveData = MutableLiveData<MutableList<TodoData>>()
    val dataProcess : DataProcessTodo by inject()
    val todoDataList : LiveData<MutableList<TodoData>> get() = todoLiveData
    private val changeStatusData = MutableLiveData<String>()
    val changeStatus : LiveData<String> get() = changeStatusData

    fun getTodoData(date: String){
      dataProcess.getTodoData(object :
          TodoDBGetCallback {
          override fun finish(todoData: MutableList<TodoData>) {
              todoLiveData.postValue(todoData)
          }
      },date)
    }

    fun insertTodoData(todoData: TodoData){
        dataProcess.insertTodoData(todoData,object :
            DBChangeCallback {
            override fun changed() {
                changeStatusData.postValue("Change")
            }
        })
    }

    fun deleteTodoData(primaryKey: Long){
        dataProcess.deleteTodoData(primaryKey)
        changeStatusData.postValue("Change")
    }

    fun updateTodoData(isChecked: Boolean,primaryKey: Long){
        dataProcess.updateTodoData(isChecked,primaryKey)
    }
}