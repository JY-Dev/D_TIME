package com.jaeyoung.d_time.adapter.diary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.activity.diary.DiaryBookMarkActivity
import com.jaeyoung.d_time.room.diary.bookmark.BookMark
import com.jaeyoung.d_time.room.diary.bookmark.BookMarkData
import kotlinx.android.synthetic.main.bookmark_item.view.title_tv
import kotlinx.android.synthetic.main.bookmark_sel_item.view.*

class BookMarkSelAdapter(context: Context) : BaseAdapter() {
    private var bookMarkData = mutableListOf<BookMarkData>()
    private var bookMarkTitle = mutableListOf<BookMark>()
    private val mContext = context
    private var addList = mutableListOf<String>()
    private var delList = mutableListOf<String>()


    override fun getView(position: Int, v: View?, p2: ViewGroup?): View {
        val view = v ?: LayoutInflater.from(mContext).inflate(R.layout.bookmark_sel_item, null)
        view.title_tv.text = bookMarkTitle[position].bookMark
        if (bookMarkData.any { it.bookMark == bookMarkTitle[position].bookMark }){
            view.add_check.isChecked = true
        }
        view.add_check.setOnCheckedChangeListener { compoundButton, isChecked ->
            val data = bookMarkTitle[position].bookMark
            if (isChecked) {
                addList.add(data)
                if(delList.contains(data))delList.remove(data)
            }
            else {
                delList.add(data)
                if(addList.contains(data)) addList.remove(data)
            }
        }
        return view
    }

    override fun getItem(p0: Int): Any {
        return bookMarkTitle[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return bookMarkTitle.size
    }

    fun setTitleData(bookMarkList: MutableList<BookMark>) {
        bookMarkTitle = bookMarkList
        notifyDataSetChanged()
    }

    fun setData(bookMarkList: MutableList<BookMarkData>) {
        bookMarkData = bookMarkList
        notifyDataSetChanged()
    }

    fun addList() : MutableList<String>{
        return addList
    }

    fun delList() : MutableList<String>{
        return delList
    }

}




