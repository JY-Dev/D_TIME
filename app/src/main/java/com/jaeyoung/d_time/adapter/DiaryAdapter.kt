package com.jaeyoung.d_time.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.room.diary.DiaryData
import kotlinx.android.synthetic.main.diary_item.view.*

class DiaryAdapter(context:Context) : BaseAdapter() {
    private val mContext = context
    var diaryDataList = mutableListOf<DiaryData>()
    var filterDataList = mutableListOf<DiaryData>()
    var filterFlag = false
    override fun getView(p0: Int, v: View?, p2: ViewGroup?): View {
        val view = v ?: LayoutInflater.from(mContext).inflate(R.layout.diary_item, null)
        view.title_tv.text = if(!filterFlag) diaryDataList[p0].title else filterDataList[p0].title
        return view
    }

    override fun getItem(p0: Int): Any {
        return if(!filterFlag)diaryDataList[p0] else filterDataList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return if(!filterFlag) diaryDataList.size else filterDataList.size
    }

    fun setDiaryData(diaryList : MutableList<DiaryData>){
        diaryDataList = diaryList
        notifyDataSetChanged()
    }

    fun filterSet(filter : String?){
        filterFlag = true
        val search = filter ?: ""
        filterDataList = diaryDataList.filter { it.title.contains(search) }.toMutableList()
        notifyDataSetChanged()
    }

    fun filterClear(){
        filterFlag = false
        notifyDataSetChanged()
    }
}