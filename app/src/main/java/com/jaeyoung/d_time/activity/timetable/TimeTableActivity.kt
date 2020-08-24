package com.jaeyoung.d_time.activity.timetable

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.utils.KeyBoardUtil
import kotlinx.android.synthetic.main.activity_diary_write.*
import kotlinx.android.synthetic.main.activity_time_table.*
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.item_timetable.*

class TimeTableActivity : AppCompatActivity() {
    val keyBoardUtil = KeyBoardUtil(this)
    var colorBtnBackList = mutableListOf(R.drawable.circle_red,R.drawable.circle_orange,R.drawable.circle_yellow,R.drawable.circle_green,R.drawable.circle_blue,R.drawable.circle_purple,R.drawable.circle_gray)
    var colorList = mutableListOf("#c6d80e0e","#c6f05c00","#c6dbe317","#c610a21b","#c61431d0","#c69223cc","#c62e2d45")
    lateinit var colorBtnList : MutableList<ImageButton>
    lateinit var mainBtnView : MutableList<View>
    var color = colorList[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_table)
        colorBtnList = mutableListOf(color_img_btn01,color_img_btn02,color_img_btn03,color_img_btn04,color_img_btn05,color_img_btn06,color_img_btn07)
        mainBtnView = mutableListOf(time_add_view,note_view,color_view)
        toolBarInit()
        layoutInit()
    }

    private fun toolBarInit() {
        setSupportActionBar(app_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_title.text = "TIME TABLE"
        back_btn.visibility = View.VISIBLE
        back_btn.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                finish()
            }
        }
    }

    private fun layoutInit(){
        note_view.setOnClickListener {
            note_et.requestFocus()
            showKeyboard()
        }
        start_time_btn.setOnClickListener(timeBtnOnClickListener)
        end_time_btn.setOnClickListener(timeBtnOnClickListener)
        clock_btn.setOnClickListener(mainBtnOnClickListener)
        note_btn.setOnClickListener(mainBtnOnClickListener)
        color_btn.setOnClickListener(mainBtnOnClickListener)
        add_btn.setOnClickListener {
            if(event_et.text.isNotEmpty()){
                timeSchedule.addItem(getTextViewParseInt(start_hour_tv),getTextViewParseInt(start_min_tv)
                    ,getTextViewParseInt(end_hour_tv),getTextViewParseInt(end_min_tv),event_et.text.toString(),color)
            } else {
                Toast.makeText(this,"Please Input Event Text",Toast.LENGTH_SHORT).show()
            }

        }
        colorBtnList.forEachIndexed { index, imageButton ->
            imageButton.setOnClickListener {
                color_btn_back.setImageResource(colorBtnBackList[index])
                color = colorList[index]
                color_view.visibility = View.GONE
            }
        }
    }

    private fun showKeyboard() {
        keyBoardUtil.showKeyboard()
    }

    private fun getTextViewParseInt(textView : TextView) : Int{
        return Integer.parseInt(textView.text.toString())
    }

    private val timeBtnOnClickListener = View.OnClickListener {view ->
        when(view){
            start_time_btn -> TimePickerDialog(this,timeStartPickerListner,Integer.parseInt(start_hour_tv.text.toString())
                                ,Integer.parseInt(start_min_tv.text.toString()),false).show()

            end_time_btn -> TimePickerDialog(this,timeEndPickerListner,Integer.parseInt(end_hour_tv.text.toString())
                ,Integer.parseInt(end_min_tv.text.toString()),false).show()
        }
    }

    private val timeStartPickerListner = TimePickerDialog.OnTimeSetListener { p0, hour, min ->
        start_hour_tv.text = if(hour<10) "0$hour" else hour.toString()
        start_min_tv.text =  if(min<10) "0$min" else min.toString()
    }

    private val timeEndPickerListner = TimePickerDialog.OnTimeSetListener { p0, hour, min ->
        end_hour_tv.text = if(hour<10) "0$hour" else hour.toString()
        end_min_tv.text =  if(min<10) "0$min" else min.toString()
    }

    private val mainBtnOnClickListener = View.OnClickListener { view ->
        mainBtnView.forEach {
            it.visibility = View.GONE
        }
        when(view){
            clock_btn -> {
                time_add_view.visibility = View.VISIBLE
            }
            note_btn -> {
                note_view.visibility = View.VISIBLE
            }
            color_btn -> {
                color_view.visibility = View.VISIBLE
            }
        }
       scrollDown()

    }

    private fun scrollDown(){
        scrollview.post {
            scrollview.fullScroll(View.FOCUS_DOWN)
        }
    }
    private fun hideKeyboard() {
        if (title_et.hasFocus()) keyBoardUtil.hideKeyboard(title_et)
        else keyBoardUtil.hideKeyboard(subtitle_et)
    }
}