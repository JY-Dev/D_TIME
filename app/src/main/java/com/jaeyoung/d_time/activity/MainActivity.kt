package com.jaeyoung.d_time.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.adapter.MainPagerAdpater
import com.jaeyoung.d_time.fragment.DiaryFragment
import com.jaeyoung.d_time.fragment.TimeTableFragment
import com.jaeyoung.d_time.fragment.TodoFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.drawer_layout.*

class MainActivity : AppCompatActivity() {
    private val titleList = mutableListOf("D TIME","DIARY","TIMETABLE")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(app_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        main_viewpager.adapter = MainPagerAdpater(supportFragmentManager, mutableListOf(TodoFragment(this,application),DiaryFragment(this),TimeTableFragment(this)))
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
    }

    override fun onBackPressed() {
        if(drawer_layout.isDrawerOpen(drawer)) drawer_layout.closeDrawer(drawer)
        else finish()
    }


}