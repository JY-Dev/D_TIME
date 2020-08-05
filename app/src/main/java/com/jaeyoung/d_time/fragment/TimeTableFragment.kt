package com.jaeyoung.d_time.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jaeyoung.d_time.R

class TimeTableFragment(context: Context) : Fragment() {
    private val mContext = context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        val view = inflater.inflate(R.layout.fragment_timetable,container,false)
        return view
    }
}