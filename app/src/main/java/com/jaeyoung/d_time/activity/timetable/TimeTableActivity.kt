package com.jaeyoung.d_time.activity.timetable

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.callback.TimeScheduleSelCallback
import com.jaeyoung.d_time.customView.TimeScheduleView
import com.jaeyoung.d_time.room.timetable.TimeTableData
import com.jaeyoung.d_time.utils.KeyBoardUtil
import com.jaeyoung.d_time.utils.getTimeString
import com.jaeyoung.d_time.utils.gson
import com.jaeyoung.d_time.utils.gsonType
import com.jaeyoung.d_time.viewModel.timetable.TimeTableViewModel
import kotlinx.android.synthetic.main.activity_diary_write.*
import kotlinx.android.synthetic.main.activity_time_table.*
import kotlinx.android.synthetic.main.app_toolbar.*

class TimeTableActivity : AppCompatActivity() {
    private val keyBoardUtil = KeyBoardUtil(this)
    private lateinit var timetableViewModel: TimeTableViewModel

    private var colorBtnBackList = mutableListOf(
        R.drawable.circle_red,
        R.drawable.circle_orange,
        R.drawable.circle_yellow,
        R.drawable.circle_green,
        R.drawable.circle_blue,
        R.drawable.circle_purple,
        R.drawable.circle_gray
    )
    private var colorList = mutableListOf(
        "#c6d80e0e",
        "#c6f05c00",
        "#c6dbe317",
        "#c610a21b",
        "#c61431d0",
        "#c69223cc",
        "#c62e2d45"
    )
    private  lateinit var colorBtnList: MutableList<ImageButton>
    private lateinit var mainFuncView: MutableList<View>
    private val modeArray = mutableListOf("add", "modify")
    private var color = colorList[0]
    private var mode = modeArray[0]
    private var date = "0"
    private var id = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_table)
        colorBtnList = mutableListOf(
            color_img_btn01,
            color_img_btn02,
            color_img_btn03,
            color_img_btn04,
            color_img_btn05,
            color_img_btn06,
            color_img_btn07
        )
        mainFuncView = mutableListOf(time_add_view, note_view, color_view)
        date = intent.extras!!.getString("date","0")
        toolBarInit()
        layoutInit()
        viewModelInit()
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

    private fun viewModelInit(){
        val androidViewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        timetableViewModel = ViewModelProvider(this,androidViewModelFactory).get(
            TimeTableViewModel::class.java)
        timetableViewModel.timeTableList.observe(this, Observer {
            timeSchedule.clearView()
            timeSchedule.setItem(it)
            clearItem(0L)
        })
        timetableViewModel.timeTable.observe(this, Observer {
            setItem(it)
        })
        timetableViewModel.status.observe(this, Observer {
            timetableViewModel.getAllData(date)
            Toast.makeText(this,"Data is Update",Toast.LENGTH_SHORT).show()
        })
        timetableViewModel.getAllData(date)
    }

    private fun layoutInit() {
        note_view.setOnClickListener {
            note_et.requestFocus()
            showKeyboard()
        }
        timeSchedule.setCallback(object : TimeScheduleSelCallback {
            override fun select(selId: Long) {
                id = selId
                timetableViewModel.getIdData(selId)
            }
        })
        start_time_btn.setOnClickListener(timeBtnOnClickListener)
        end_time_btn.setOnClickListener(timeBtnOnClickListener)
        clock_btn.setOnClickListener(mainBtnOnClickListener)
        note_btn.setOnClickListener(mainBtnOnClickListener)
        color_btn.setOnClickListener(mainBtnOnClickListener)
        add_btn.setOnClickListener {
            clearItem(0L)
        }
        date_tv.text = date
        del_btn.setOnClickListener {

            if(id!=0L) timetableViewModel.deleteData(id)
            else clearItem(0L)
        }
        ok_btn.setOnClickListener {
            if (event_et.text.isNotEmpty()) {
                val timeData = TimeScheduleView.TimeData(
                    getTextViewParseInt(start_hour_tv), getTextViewParseInt(start_min_tv)
                    , getTextViewParseInt(end_hour_tv), getTextViewParseInt(end_min_tv)
                )
                if (timeSchedule.checkItem(timeData,id)) {
                    if(id==0L) id = System.currentTimeMillis()
                    val timeTableData = TimeTableData(id, date, event_et.text.toString(), note_et.text.toString(), color, gson.toJson(timeData,gsonType.type))
                    when (mode) {
                        modeArray[0] -> {
                            timetableViewModel.insertData(timeTableData)
                        }
                        modeArray[1] -> {
                            timetableViewModel.updateData(timeTableData)
                        }
                    }
                } else {
                    Toast.makeText(this, "Overlap Time Schedule", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please Input Event Text", Toast.LENGTH_SHORT).show()
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

    private fun getTextViewParseInt(textView: TextView): Int {
        return Integer.parseInt(textView.text.toString())
    }

    private val timeBtnOnClickListener = View.OnClickListener { view ->
        when (view) {
            start_time_btn -> TimePickerDialog(
                this, timeStartPickerListner, Integer.parseInt(start_hour_tv.text.toString())
                , Integer.parseInt(start_min_tv.text.toString()), false
            ).show()

            end_time_btn -> TimePickerDialog(
                this, timeEndPickerListner, Integer.parseInt(end_hour_tv.text.toString())
                , Integer.parseInt(end_min_tv.text.toString()), false
            ).show()
        }
    }

    private val timeStartPickerListner = TimePickerDialog.OnTimeSetListener { p0, hour, min ->
        start_hour_tv.text = getTimeString(hour)
        start_min_tv.text = getTimeString(min)
    }

    private val timeEndPickerListner = TimePickerDialog.OnTimeSetListener { p0, hour, min ->
        end_hour_tv.text = getTimeString(hour)
        end_min_tv.text = getTimeString(min)
    }

    private val mainBtnOnClickListener = View.OnClickListener { view ->
        allViewGone()
        when (view) {
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



    private fun allViewGone() {
        mainFuncView.forEach {
            it.visibility = View.GONE
        }
    }

    private fun scrollDown() {
        scrollview.post {
            scrollview.fullScroll(View.FOCUS_DOWN)
        }
    }

    private fun hideKeyboard() {
        if (title_et.hasFocus()) keyBoardUtil.hideKeyboard(title_et)
        else keyBoardUtil.hideKeyboard(subtitle_et)
    }

    private fun setItem(timeTableData: TimeTableData){
        val timeData = gson.fromJson(timeTableData.timeData,gsonType.type) as TimeScheduleView.TimeData
        clearItem(timeTableData.id)
        event_et.setText(timeTableData.event)
        note_et.setText(timeTableData.note)
        start_hour_tv.text = getTimeString(timeData.startHour)
        start_min_tv.text = getTimeString(timeData.startMin)
        end_hour_tv.text = getTimeString(timeData.endHour)
        end_min_tv.text = getTimeString(timeData.endMin)
        color = timeTableData.color
        color_btn_back.setImageResource(colorBtnBackList[colorList.indexOf(color)])
    }

    private fun clearItem(targetId:Long) {
        id = targetId
        mode = if(id==0L) modeArray[0] else modeArray[1]
        event_et.text.clear()
        note_et.text.clear()
        start_hour_tv.text = getTimeString(0)
        start_min_tv.text = getTimeString(0)
        end_hour_tv.text = getTimeString(0)
        end_min_tv.text = getTimeString(0)
        color = colorList[0]
        color_btn_back.setImageResource(colorBtnBackList[0])
        allViewGone()
    }
}