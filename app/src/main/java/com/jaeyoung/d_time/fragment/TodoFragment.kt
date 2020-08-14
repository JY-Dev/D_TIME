package com.jaeyoung.d_time.fragment

import android.app.Application
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.activity.MainActivity
import com.jaeyoung.d_time.adapter.TodoAdapter
import com.jaeyoung.d_time.adapter.TodoItemDecoration
import com.jaeyoung.d_time.room.todo.TodoData
import com.jaeyoung.d_time.viewModel.TodoViewModel
import kotlinx.android.synthetic.main.fragment_todo.view.*
import java.text.SimpleDateFormat
import java.util.*


class TodoFragment(
    context: Context,
    application: Application
) : Fragment() {
    private val mContext = context
    private val mainActivity = context as MainActivity
    private val mApplication = application
    private var cal = Calendar.getInstance()
    private val calViewModel = mainActivity.getCalendarViewModel()
    lateinit var todoViewModel: TodoViewModel
    lateinit var todoItemAdpater: TodoAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var itemDecoration: TodoItemDecoration
    var imm : InputMethodManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_todo, container, false)
        linearLayoutManager = LinearLayoutManager(mContext)
        itemDecoration = TodoItemDecoration(mContext)
        imm = mContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        //View Model
        val androidViewModelFactory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(mApplication)
        todoViewModel =
            ViewModelProvider(this, androidViewModelFactory).get(TodoViewModel::class.java)
        todoViewModel.todoDataList.observe(this, Observer {
            todoItemAdpater.setData(it)
            view.todo_listview.smoothScrollToPosition(todoItemAdpater.itemCount)
            if (todoItemAdpater.itemCount == 0) listVisiblity(view, true)
            else listVisiblity(view, false)
        })
        todoViewModel.changeStatus.observe(this, Observer {
            todoViewModel.getTodoData(getDate(cal))
        })
        todoItemAdpater = TodoAdapter(mContext,todoViewModel)
        calViewModel.calData.observe(this, Observer {
            cal = it
            mainActivity.dismissCalendarDialog()
            todoViewModel.getTodoData(getDate(cal))

        })
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                todoViewModel.deleteTodoData(
                    todoItemAdpater.getItem(viewHolder.adapterPosition).id
                )
            }
        }).attachToRecyclerView(view.todo_listview)
        view.todo_listview.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            addItemDecoration(itemDecoration)
            // specify an viewAdapter (see also next example)
            adapter = todoItemAdpater
        }

        todoViewModel.getTodoData(getDate(cal))



        view.add_btn.setOnClickListener {
            if (view.todo_et.text.isNotEmpty()) {
                todoViewModel.insertTodoData(
                    TodoData(
                        System.currentTimeMillis(),
                        getDate(cal),
                        view.todo_et.text.toString(),
                        false
                    )
                )
                view.todo_et.text.clear()
            }
            todoViewModel.getTodoData(getDate(cal))
            imm?.hideSoftInputFromWindow(view.todo_et.windowToken,0)
        }

        view.date_btn.setOnClickListener {
            mainActivity.showCalendarDialog()
        }
        return view
    }


    private fun listVisiblity(view: View, isEmpty: Boolean) {
        view.todo_listempty.visibility = if (isEmpty) View.VISIBLE else View.GONE
        view.todo_listview.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    private fun getDate(cal: Calendar): String {
        val simpleFormat = SimpleDateFormat("yyyy-MM-dd")
        return simpleFormat.format(cal.time)
    }
}