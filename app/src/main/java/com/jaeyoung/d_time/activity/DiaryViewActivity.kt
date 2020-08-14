package com.jaeyoung.d_time.activity

import android.content.Intent
import android.content.res.TypedArray
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.viewModel.DiaryViewModel
import kotlinx.android.synthetic.main.activity_diary_view.*
import kotlinx.android.synthetic.main.activity_diary_write.diary_date_tv
import kotlinx.android.synthetic.main.activity_diary_write.image_layout
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.diary_image_layout.view.*

class DiaryViewActivity : AppCompatActivity() {
    lateinit var diaryViewModel: DiaryViewModel
    val listType: TypeToken<MutableList<String>> = object : TypeToken<MutableList<String>>() {}
    val gson = GsonBuilder().create()
    lateinit var emotionImgArray: TypedArray
    lateinit var weatherImgArray: TypedArray
    var primaryKey = 0L
    var date = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_view)
        emotionImgArray = resources.obtainTypedArray(R.array.emotion_image)
        weatherImgArray = resources.obtainTypedArray(R.array.weather_image)
        toolBarInit()
        viewModelInit()
        layoutInit()
    }

    /**
     * Toolbar Init
     */
    private fun toolBarInit(){
        setSupportActionBar(app_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_title.text = "MY DIARY"
        back_btn.visibility = View.VISIBLE
        back_btn.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                finish()
            }
        }
    }

    private fun viewModelInit() {
        val androidViewModelFactory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        diaryViewModel = ViewModelProvider(this, androidViewModelFactory).get(
            DiaryViewModel::class.java
        )
        diaryViewModel.status.observe(this, Observer {
            finish()
        })
        primaryKey = intent.getLongExtra("primary", 0)
        diaryViewModel.diary.observe(this, Observer { diaryData ->
            emotion_img.setImageDrawable(emotionImgArray.getDrawable(diaryData.emotion))
            weahter_img.setImageDrawable(weatherImgArray.getDrawable(diaryData.weather))
            diary_date_tv.text = diaryData.date
            title_tv.text = diaryData.title
            subtitle_tv.text = diaryData.subTitle
            date = diaryData.date
            image_layout.removeAllViews()
            val imgDataSet = gson.fromJson(diaryData.imgUriArray, listType.type) as MutableList<String>
            imgDataSet.forEach {
                imageAdd(Uri.parse(it))
            }
        })
    }

    private fun layoutInit() {
        del_btn.setOnClickListener {
            diaryViewModel.deleteData(primaryKey)
        }

        modify_btn.setOnClickListener {
            val intent = Intent(Intent(this, DiaryWriteActivity::class.java))
            intent.putExtra("date", date)
            intent.putExtra("modify", true)
            intent.putExtra("primary", primaryKey)
            startActivity(intent)
        }
    }

    private fun imageAdd(uri: Uri?) {
        val view = layoutInflater.inflate(R.layout.diary_image_layout, null) as LinearLayout
        view.diary_img.apply {
            setImageURI(uri)
            clipToOutline = true
        }
        view.del_btn.visibility = View.GONE
        image_layout.addView(view)
    }



    override fun onResume() {
        diaryViewModel.getDiaryOneData(primaryKey)
        super.onResume()
    }
}