package com.jaeyoung.d_time.activity

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.app_toolbar.*

open class BaseActivity : AppCompatActivity() {

    /**
     * Toolbar Init
     */
    fun toolBarInit(title:String){
        setSupportActionBar(app_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_title.text = title
        back_btn.visibility = View.VISIBLE
        back_btn.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                finish()
            }
        }
    }

    /**
     * MainToolBar Init
     */
    fun toolBarInit(){
            setSupportActionBar(app_toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            drawer_btn.visibility = View.VISIBLE
    }
}