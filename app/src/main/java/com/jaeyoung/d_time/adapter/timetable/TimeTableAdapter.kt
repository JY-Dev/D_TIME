package com.jaeyoung.d_time.adapter.timetable

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.room.timetable.TimeTableData
import com.jaeyoung.d_time.utils.getTime
import com.jaeyoung.d_time.utils.getTimeData
import kotlinx.android.synthetic.main.item_timetable.view.*

class TimeTableAdapter(context: Context) : BaseAdapter() {
    private var timeTableData = mutableListOf<TimeTableData>()
    private val mContext = context

    override fun getView(position: Int, v: View?, p2: ViewGroup?): View {
        val view = v ?: LayoutInflater.from(mContext).inflate(R.layout.item_timetable, null)
        val timeData = getTimeData(timeTableData[position].timeData)
        view.start_time_tv.text = getTime(timeData.startHour,timeData.startMin)
        view.end_time_tv.text = getTime(timeData.endHour,timeData.endMin)
        view.title_tv.text = timeTableData[position].event
        return view
    }

    override fun getItem(p0: Int): TimeTableData {
        return timeTableData[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return timeTableData.size
    }

    fun setData(timeTableList: MutableList<TimeTableData>){
        timeTableData = timeTableList
        notifyDataSetChanged()
    }


}




