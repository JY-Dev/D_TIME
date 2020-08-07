package com.jaeyoung.d_time.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.adapter.DiarySpinnerAdapter
import kotlinx.android.synthetic.main.activity_diary_write.*
import kotlinx.android.synthetic.main.activity_diary_write.view.*
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.diary_spinner_row.view.*

class DiaryWriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_write)
        setSupportActionBar(app_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        weather_spinner.adapter = DiarySpinnerAdapter(this,resources.obtainTypedArray(R.array.weather_image))
        emotion_spinner.adapter = DiarySpinnerAdapter(this,resources.obtainTypedArray(R.array.emotion_image))
        weather_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

        }

        toolbar_title.text = "WRITING"
        back_btn.visibility = View.VISIBLE
        back_btn.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                finish()
            }
        }

    }
}