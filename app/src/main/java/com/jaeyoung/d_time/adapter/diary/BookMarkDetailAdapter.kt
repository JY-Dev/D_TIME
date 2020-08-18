package com.jaeyoung.d_time.adapter.diary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.activity.diary.DiaryBookMarkDetailActivity
import com.jaeyoung.d_time.room.diary.DiaryData
import com.jaeyoung.d_time.room.diary.bookmark.BookMarkData
import kotlinx.android.synthetic.main.bookmark_item.view.*

class BookMarkDetailAdapter(context: Context) : BaseAdapter() {
    private var diaryData = mutableListOf<DiaryData>()
    private val mContext = context
    private val diaryBookMarkDetailActivity = mContext as DiaryBookMarkDetailActivity
    private var deleteFlag = false
    private var deleteList = mutableListOf<DiaryData>()

    override fun getView(position: Int, v: View?, p2: ViewGroup?): View {
        val view = v ?: LayoutInflater.from(mContext).inflate(R.layout.bookmark_item, null)
        if (deleteFlag) view.del_check.visibility = View.VISIBLE
        else view.del_check.visibility = View.GONE
        view.title_tv.text = getData()[position].title
        view.del_check.isChecked = false
        view.del_check.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) deleteList.add(getData()[position])
            else deleteList.remove(getData()[position])
        }
        return view
    }

    override fun getItem(p0: Int): Any {
        return diaryData[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return diaryData.size
    }

    fun setData(bookMarkList: DiaryData) {
        diaryData.add(bookMarkList)
        notifyDataSetChanged()
    }

    fun getData(): MutableList<DiaryData> {
        return diaryData
    }

    fun deleteFlag(): Boolean {
        deleteFlag = !deleteFlag
        notifyDataSetChanged()
        return deleteFlag
    }

    fun clearList(){
        diaryData.clear()
        notifyDataSetChanged()
    }

    fun removeList() {
        deleteList.forEach {
           diaryBookMarkDetailActivity.delData(it.id)
        }
        notifyDataSetChanged()
    }

}




