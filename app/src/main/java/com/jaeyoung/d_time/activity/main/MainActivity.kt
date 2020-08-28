package com.jaeyoung.d_time.activity.main

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.adapter.main.MainPagerAdpater
import com.jaeyoung.d_time.dialog.CalendarDialog
import com.jaeyoung.d_time.fragment.DiaryFragment
import com.jaeyoung.d_time.fragment.TimeTableFragment
import com.jaeyoung.d_time.fragment.TodoFragment
import com.jaeyoung.d_time.viewModel.calendar.CalendarViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.drawer_layout.*

class MainActivity : AppCompatActivity() {
    private val titleList = mutableListOf("D TIME","DIARY","TIMETABLE")
    private lateinit var calendarViewModel: CalendarViewModel
    private lateinit var calendarDialog: CalendarDialog
    private var backKeyPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolBarInit()
        viewModelInit()
        viewInit()

    }

    /**
     * ToolBar Init
     */
    private fun toolBarInit(){
        setSupportActionBar(app_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        drawer_btn.visibility = View.VISIBLE
    }

    /**
     * TodoViewModel Init
     */
    private fun viewModelInit(){
        val androidViewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        calendarViewModel = ViewModelProvider(this,androidViewModelFactory).get(CalendarViewModel::class.java)
        calendarDialog = CalendarDialog(this,calendarViewModel)
    }

    /**
     * UI Init
     */
    private fun viewInit(){
        main_viewpager.adapter =
            MainPagerAdpater(
                supportFragmentManager,
                mutableListOf(
                    TodoFragment(this, application),
                    DiaryFragment(this, application),
                    TimeTableFragment(this , application)
                )
            )
        main_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                toolbar_title.text = titleList[position]
            }


        })
        drawer_btn.let {
            it.visibility = View.VISIBLE
            it.setOnClickListener {
                drawer_layout.openDrawer(drawer)
            }
        }

        back_drawer_btn.setOnClickListener {
            drawer_layout.closeDrawer(drawer)
        }
    }

    override fun onBackPressed() {
        if(drawer_layout.isDrawerOpen(drawer)) drawer_layout.closeDrawer(drawer)
        else {
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis()
                Toast.makeText(this, "Press the 'Back' button one more time to finish.", Toast.LENGTH_SHORT).show()
                return
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                finish()
            }
        }
    }

    fun getCalendarViewModel() : CalendarViewModel {
        return calendarViewModel
    }

    fun showCalendarDialog(){
        calendarDialog.show()
    }

    fun dismissCalendarDialog(){
        calendarDialog.dismiss()
    }

}