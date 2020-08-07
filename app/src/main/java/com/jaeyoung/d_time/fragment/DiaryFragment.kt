package com.jaeyoung.d_time.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.activity.DiaryWriteActivity
import com.jaeyoung.d_time.activity.MainActivity
import com.jaeyoung.d_time.adapter.DiarySpinnerAdapter
import com.jaeyoung.d_time.dialog.CalendarDialog
import com.jaeyoung.d_time.viewModel.CalendarViewModel
import kotlinx.android.synthetic.main.activity_diary_write.view.*
import kotlinx.android.synthetic.main.fragment_diary.view.*
import java.util.*

class DiaryFragment(context: Context) : Fragment() {
    private val mContext = context
    private val mainActivity = context as MainActivity
    private val calViewModel = mainActivity.getCalendarViewModel()
    private var cal = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_diary,container,false)

        calViewModel.calData.observe(this, androidx.lifecycle.Observer {
            cal = it
            mainActivity.dismissCalendarDialog()
        })
        view.cal_btn.setOnClickListener {
            mainActivity.showCalendarDialog()
        }
        view.write_btn.setOnClickListener {
            startActivity(Intent(mContext,DiaryWriteActivity::class.java))
        }
        return view
    }

}