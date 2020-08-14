package com.jaeyoung.d_time.adapter.todo

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jaeyoung.d_time.room.todo.TodoData
import com.jaeyoung.d_time.viewModel.todo.TodoViewModel
import kotlinx.android.synthetic.main.todo_item.view.*


class TodoAdapter(context: Context,todoViewModel: TodoViewModel) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    private var todoData = mutableListOf<TodoData>()
    private val mContext = context
    private val mTodoViewModel = todoViewModel

    override fun getItemCount(): Int {
        return todoData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTv.text = todoData[position].title
        holder.clearCheckBox.let {
            it.setOnClickListener {
                mTodoViewModel.updateTodoData(holder.clearCheckBox.isChecked,todoData[position].id)
            }
            it.setOnCheckedChangeListener { compoundButton, isChecked ->
                if(isChecked) holder.titleTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                else holder.titleTv.paintFlags = 0
            }
            it.isChecked = todoData[position].isClear
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(com.jaeyoung.d_time.R.layout.todo_item, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTv: TextView = itemView.title_todo
        var clearCheckBox : CheckBox = itemView.clear_check
    }

    fun setData(todoData: MutableList<TodoData>) {
        this.todoData = todoData
        notifyDataSetChanged()
    }

    fun getItem(position: Int) : TodoData {
        return todoData[position]
    }
}