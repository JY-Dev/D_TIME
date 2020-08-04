package com.jaeyoung.d_time

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.jaeyoung.d_time.room.TodoData
import kotlinx.android.synthetic.main.todo_item.view.*

class TodoItemAdpater(context : Context) : BaseAdapter() {
    private var todoData = mutableListOf<TodoData>()
    private val mContext = context
    override fun getView(position: Int, convertView: View?, p2: ViewGroup?): View {
        val view : View = convertView ?: LayoutInflater.from(mContext).inflate(R.layout.todo_item, null)
        view.title_todo.text = todoData[position].title
        return view
    }

    override fun getItem(p0: Int): Any {
        return todoData[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return todoData.size
    }

    fun setData(todoData:MutableList<TodoData>){
        this.todoData = todoData
        notifyDataSetChanged()
    }
}