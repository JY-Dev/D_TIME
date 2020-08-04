package com.jaeyoung.d_time.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.TodoAdapter
import com.jaeyoung.d_time.TodoItemAdpater
import com.jaeyoung.d_time.TodoItemDecoration
import com.jaeyoung.d_time.utils.DataProcess
import com.jaeyoung.d_time.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.drawer_layout.*

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    lateinit var todoItemAdpater: TodoAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var itemDecoration: TodoItemDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){
        setSupportActionBar(app_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        todoItemAdpater = TodoAdapter(this)
        linearLayoutManager = LinearLayoutManager(this)
        itemDecoration = TodoItemDecoration(this)
        //View Model
        val androidViewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        mainViewModel = ViewModelProvider(this,androidViewModelFactory).get(MainViewModel::class.java)
        mainViewModel.todoDataList.observe(this, Observer {
            todoItemAdpater.setData(it)
            todo_listview.smoothScrollToPosition(todoItemAdpater.itemCount)
        })
        todo_listview.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            addItemDecoration(itemDecoration)
            // specify an viewAdapter (see also next example)
            adapter = todoItemAdpater
        }

        mainViewModel.getTodoData()

        drawer_btn.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                drawer_layout.openDrawer(drawer)
            }
        }

        add_btn.setOnClickListener {
            if(todo_et.text.isNotEmpty()) {
                mainViewModel.insertTodoData("2010-03-02", todo_et.text.toString())
                todo_et.text.clear()
            }
            mainViewModel.getTodoData()
        }

        date_btn.setOnClickListener {

        }


    }

    override fun onBackPressed() {
        if(drawer_layout.isDrawerOpen(drawer)) drawer_layout.closeDrawer(drawer)
        else finish()
    }
}