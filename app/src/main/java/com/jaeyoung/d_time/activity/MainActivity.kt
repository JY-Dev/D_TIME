package com.jaeyoung.d_time.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jaeyoung.d_time.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.drawer_layout.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){
        setSupportActionBar(app_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
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