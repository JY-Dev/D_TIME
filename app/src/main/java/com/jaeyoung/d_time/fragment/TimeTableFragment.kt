package com.jaeyoung.d_time.fragment

import android.app.Application
import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.activity.main.MainActivity
import com.jaeyoung.d_time.activity.timetable.TimeTableActivity
import com.jaeyoung.d_time.adapter.timetable.TimeTableAdapter
import com.jaeyoung.d_time.utils.getTimeData
import com.jaeyoung.d_time.viewModel.timetable.TimeTableViewModel
import kotlinx.android.synthetic.main.fragment_timetable.view.*
import java.text.SimpleDateFormat
import java.util.*

class TimeTableFragment(context: Context,application:Application) : Fragment() {
    private val mContext = context
    private val mainActivity = context as MainActivity
    private val mApplication = application
    private var cal = Calendar.getInstance()
    private val calViewModel = mainActivity.getCalendarViewModel()
    private lateinit var timetableViewModel: TimeTableViewModel
    private lateinit var timeTableAdapter: TimeTableAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        val view = inflater.inflate(R.layout.fragment_timetable,container,false)
        timeTableAdapter = TimeTableAdapter(mContext)
        view.timeTable_list.apply {
            adapter = timeTableAdapter
            setOnItemClickListener { _, _, position, _ ->
                val intent = Intent(mContext,TimeTableActivity::class.java)
                intent.putExtra("date",getDate(cal))
                intent.putExtra("id",timeTableAdapter.getItem(position).id)
                startActivity(intent)
            }
        }
        viewModelInit()
        view.cal_btn.setOnClickListener {
            mainActivity.showCalendarDialog()
        }
        view.add_btn.setOnClickListener {
            val intent = Intent(mContext,TimeTableActivity::class.java)
            intent.putExtra("date",getDate(cal))
            startActivity(intent)
        }
        return view
    }

    private fun viewModelInit(){
        val androidViewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(mApplication)
        timetableViewModel = ViewModelProvider(this,androidViewModelFactory).get(
            TimeTableViewModel::class.java)
        timetableViewModel.timeTableList.observe(this, androidx.lifecycle.Observer {
            val data = it
            data.sortBy { getTimeData(it.timeData).startHour*60+ getTimeData(it.timeData).startMin}
            timeTableAdapter.setData(data)
        })
        timetableViewModel.getAllData(getDate(cal))
        calViewModel.calData.observe(this, androidx.lifecycle.Observer {
            cal = it
            timetableViewModel.getAllData(getDate(cal))
            mainActivity.dismissCalendarDialog()

        })
    }

    private fun getDate(cal: Calendar): String {
        val simpleFormat = SimpleDateFormat("yyyy.MM.dd")
        return simpleFormat.format(cal.time)
    }

    override fun onResume() {
        timetableViewModel.getAllData(getDate(cal))
        super.onResume()

    }
}