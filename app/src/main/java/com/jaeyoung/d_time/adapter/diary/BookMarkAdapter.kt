package com.jaeyoung.d_time.adapter.diary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.lifecycle.LiveData
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.room.diary.bookmark.BookMarkData
import kotlinx.android.synthetic.main.bookmark_item.view.*

class BookMarkAdapter(context: Context) : BaseAdapter() {
    private var bookMarkData = mutableListOf<BookMarkData>()
    private val mContext = context
    private var deleteFlag = false
    private var deleteList = mutableListOf<Int>()

    override fun getView(position: Int, v: View?, p2: ViewGroup?): View {
        val view = v ?: LayoutInflater.from(mContext).inflate(R.layout.bookmark_item, null)
        if(deleteFlag) view.del_check.visibility = View.VISIBLE
        else view.del_check.visibility = View.GONE
        view.del_check.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked) deleteList.add(position)
            else deleteList.remove(position)
        }
        return view
    }

    override fun getItem(p0: Int): Any {
        return bookMarkData[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return bookMarkData.size
    }

    fun addData(bookMark : BookMarkData) {
        bookMarkData.add(bookMark)
        notifyDataSetChanged()
    }

    fun setData(bookMarkList : MutableList<BookMarkData>) {
        bookMarkData = bookMarkList
        notifyDataSetChanged()
    }
    fun getData() : MutableList<BookMarkData>{
        return bookMarkData
    }

    fun deleteFlag() : Boolean{
        deleteFlag = !deleteFlag
        notifyDataSetChanged()
        return deleteFlag
    }

    fun removeList(){
        deleteList.forEach {
            bookMarkData.removeAt(it)
        }
        notifyDataSetChanged()
    }
}




