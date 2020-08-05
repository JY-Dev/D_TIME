package com.jaeyoung.d_time.fragment

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.adapter.TodoAdapter
import com.jaeyoung.d_time.adapter.TodoItemDecoration
import com.jaeyoung.d_time.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_todo.view.*

class TodoFragment(context: Context , application: Application) : Fragment() {
    private val mContext = context
    private val mApplication = application
    lateinit var mainViewModel: MainViewModel
    lateinit var todoItemAdpater: TodoAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var itemDecoration: TodoItemDecoration

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View{
        val view = inflater.inflate(R.layout.fragment_todo,container,false)
        todoItemAdpater = TodoAdapter(mContext)
        linearLayoutManager = LinearLayoutManager(mContext)
        itemDecoration = TodoItemDecoration(mContext)
        //View Model
        val androidViewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(mApplication)
        mainViewModel = ViewModelProvider(this,androidViewModelFactory).get(MainViewModel::class.java)
        mainViewModel.todoDataList.observe(this, Observer {
            todoItemAdpater.setData(it)
            view.todo_listview.smoothScrollToPosition(todoItemAdpater.itemCount)
            if(todoItemAdpater.itemCount==0) listVisiblity(view,true)
            else listVisiblity(view,false)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
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
        }).attachToRecyclerView(view.todo_listview)
        view.todo_listview.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            addItemDecoration(itemDecoration)
            // specify an viewAdapter (see also next example)
            adapter = todoItemAdpater
        }

        mainViewModel.getTodoData()



        view.add_btn.setOnClickListener {
            if(view.todo_et.text.isNotEmpty()) {
                mainViewModel.insertTodoData("2010-03-02", view.todo_et.text.toString())
                view.todo_et.text.clear()
            }
            mainViewModel.getTodoData()
        }

        view.date_btn.setOnClickListener {

        }
        return view
    }


    private fun listVisiblity(view:View,isEmpty:Boolean){
        view.todo_listempty.visibility = if (isEmpty) View.VISIBLE else View.GONE
        view.todo_listview.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }
}