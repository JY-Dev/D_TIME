package com.jaeyoung.d_time.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jaeyoung.d_time.room.TodoData
import kotlinx.android.synthetic.main.todo_item.view.*


class TodoAdapter(context: Context) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    private var todoData = mutableListOf<TodoData>()
    private val mContext = context

    override fun getItemCount(): Int {
        return todoData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTv.text = todoData[position].title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(com.jaeyoung.d_time.R.layout.todo_item, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTv: TextView = itemView.title_todo
    }

    fun setData(todoData: MutableList<TodoData>) {
        this.todoData = todoData
        notifyDataSetChanged()
    }

    fun getItem(position: Int) : TodoData{
        return todoData[position]
    }
}