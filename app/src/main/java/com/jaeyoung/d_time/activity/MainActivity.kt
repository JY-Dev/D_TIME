package com.jaeyoung.d_time.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.adapter.TodoAdapter
import com.jaeyoung.d_time.adapter.TodoItemDecoration
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
            if(todoItemAdpater.itemCount==0) listVisiblity(true)
            else listVisiblity(false)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                mainViewModel.deleteTodoData(todoItemAdpater.getItem(viewHolder.adapterPosition).id)
            }
        }).attachToRecyclerView(todo_listview)
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

    private fun listVisiblity(isEmpty:Boolean){
        todo_listempty.visibility = if (isEmpty) View.VISIBLE else View.GONE
        todo_listview.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    override fun onBackPressed() {
        if(drawer_layout.isDrawerOpen(drawer)) drawer_layout.closeDrawer(drawer)
        else finish()
    }


}