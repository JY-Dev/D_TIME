package com.jaeyoung.d_time.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.MotionEvent
import android.view.Window
import android.widget.Toast
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.viewModel.calendar.CalendarViewModel
import kotlinx.android.synthetic.main.dialog_cal.*

class CalendarDialog(context: Context,calendarViewModel: CalendarViewModel) : Dialog(context) {
    private val mContext =context


    init {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setContentView(R.layout.dialog_cal)
        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.setCanceledOnTouchOutside(false)

        cal_view.setCalViewModel(calendarViewModel)
    }

}