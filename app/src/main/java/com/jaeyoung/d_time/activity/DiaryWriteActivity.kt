package com.jaeyoung.d_time.activity

import android.app.Activity
import android.content.Intent
import android.content.res.TypedArray
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.adapter.DiarySpinnerAdapter
import com.jaeyoung.d_time.room.diary.DiaryData
import com.jaeyoung.d_time.utils.CameraUtil
import com.jaeyoung.d_time.utils.DiaryPopup
import com.jaeyoung.d_time.utils.KeyBoardUtil
import com.jaeyoung.d_time.utils.dp
import com.jaeyoung.d_time.viewModel.DiaryWriteViewModel
import kotlinx.android.synthetic.main.activity_diary_write.*
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.diary_image_layout.view.*

class DiaryWriteActivity : AppCompatActivity() {
    var mPopupList = mutableListOf<PopupWindow>()
    var mPopupViewList = mutableListOf<View>()
    lateinit var cameraUtil : CameraUtil
    var imgArray = mutableListOf<String>()
    lateinit var diaryWriteViewModel: DiaryWriteViewModel
    var modify = false
    var primaryKey = 0L
    val gson = GsonBuilder().create()
    val listType : TypeToken<MutableList<String>> = object : TypeToken<MutableList<String>>() {}
    val keyBoardUtil = KeyBoardUtil(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_write)
        toolBarInit()
        viewModelInit()
        spinnerInit()
        layoutInit()
        popupWindowInit()
        if(modify) modifyInit()
    }

    /**
     * Toolbar Init
     */
    private fun toolBarInit(){
        setSupportActionBar(app_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_title.text = "WRITING"
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
        diaryWriteViewModel = ViewModelProvider(this,androidViewModelFactory).get(DiaryWriteViewModel::class.java)
        diaryWriteViewModel.status.observe(this, Observer {
            finish()
        })
        diaryWriteViewModel.modify.observe(this, Observer {
            modifySet(it)
        })
    }

    private fun layoutInit(){
        subtitle_view.setOnClickListener {
            subtitle_et.requestFocus()
            showKeyboard()
        }
        diary_date_tv.text = intent.getStringExtra("date")
        modify = intent.getBooleanExtra("modify",false)
        cameraUtil = CameraUtil(this)
    }

    private fun modifyInit(){
        primaryKey = intent.getLongExtra("primary",0)
        diaryWriteViewModel.modifyGetData(primaryKey)
    }

    /**
     * Spinner Init (Weather, Emotion)
     */
    private fun spinnerInit(){
        val touch = object : View.OnTouchListener{
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                hideKeyboard()
                return false
            }
        }
        weather_spinner.let {
            it.adapter = DiarySpinnerAdapter(this,resources.obtainTypedArray(R.array.weather_image))
            it.setOnTouchListener(touch)
        }
        emotion_spinner.let {
            it.adapter = DiarySpinnerAdapter(this,resources.obtainTypedArray(R.array.emotion_image))
            it.setOnTouchListener(touch)
        }
    }

    /**
     * PopupWindow Init (Edit, More)
     */
    private fun popupWindowInit(){
        mPopupViewList = mutableListOf(
            popupView(resources.obtainTypedArray(R.array.edit_image),resources.obtainTypedArray(R.array.edit_id)),
            popupView(resources.obtainTypedArray(R.array.more_image),resources.obtainTypedArray(R.array.more_id)))
        mPopupViewList.forEach {
            mPopupList.add(PopupWindow(it, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        }
        save_btn.setOnClickListener{
            val primary = if(primaryKey==0L) System.currentTimeMillis() else primaryKey
            val diaryData = DiaryData(primary, diary_date_tv.text.toString(),
                title_et.text.toString(), subtitle_et.text.toString(),
                gson.toJson(imgArray,listType.type), weather_spinner.selectedItemPosition, emotion_spinner.selectedItemPosition)
            if(title_et.text.isNotEmpty()){
                when(modify){
                    true -> diaryWriteViewModel.updateData(diaryData)
                    false -> diaryWriteViewModel.insertData(diaryData)
                }
            } else {
                Toast.makeText(this,"Please Input Title",Toast.LENGTH_SHORT).show()
            }
        }
        edit_btn.setOnClickListener(popupViewOnClick)
        whole_view.setOnClickListener {
            mPopupList.filter { popup -> popup.isShowing }[0].dismiss()
            it.visibility = View.GONE
        }
    }


    private fun modifySet(diaryData : DiaryData){
        title_et.setText(diaryData.title)
        subtitle_et.setText(diaryData.subTitle)
        weather_spinner.setSelection(diaryData.weather)
        emotion_spinner.setSelection(diaryData.emotion)
        val imgDataSet = gson.fromJson(diaryData.imgUriArray,listType.type) as MutableList<String>
        imgDataSet.forEach {
            imageAdd(Uri.parse(it))
        }
    }
    /**
     * Popup View Set
     */
    private fun popupView(imageArray: TypedArray,idArray:TypedArray) : View{
        val view = layoutInflater.inflate(R.layout.popup_view, null) as LinearLayout
        for(i in 0 until imageArray.length()){
            val imageView = ImageView(this)
            val lineView = View(this)
            val lineViewLP = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1.dp)
            lineViewLP.apply {
                topMargin = 6.dp
                bottomMargin = 6.dp
            }
            lineView.apply {
                setBackgroundColor(Color.BLACK)
                layoutParams = lineViewLP
            }
            imageView.apply {
                layoutParams = LinearLayout.LayoutParams(25.dp, 25.dp)
                setImageDrawable(imageArray.getDrawable(i))
                id = idArray.getResourceId(i,0)
                setOnClickListener(popupItemOnClick)
            }
            view.addView(imageView)
            if (i!=imageArray.length()-1) view.addView(lineView)
        }
        return view
    }

    /**
     * PopupWindow OnclickListener
     */
    private val popupViewOnClick = View.OnClickListener { view ->
        when(view){
            edit_btn -> mPopupList[DiaryPopup.EDIT.ordinal].showAsDropDown(view, 0,10.dp)
        }
        whole_view.visibility = View.VISIBLE
        hideKeyboard()
    }

    /**
     * PopupItem OnclickListener
     */
    private val popupItemOnClick = View.OnClickListener { view ->
        when(view.id){
            // Take a Picture
            R.id.camera_btn ->{
                cameraUtil.captureCamera()
            }
            // Take a Album
            R.id.photo_btn ->{
                cameraUtil.getAlbum()
            }
        }
        whole_view.performClick()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode != RESULT_OK) return
        when(requestCode){
            cameraUtil.REQUEST_TAKE_PHOTO -> {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        cameraUtil.galleryAddPic()
                        imageAdd(cameraUtil.getImageUri())
                    } catch (e: Exception) {
                    }

                } else {
                    Toast.makeText(this, "사진찍기를 취소하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            cameraUtil.REQUEST_TAKE_ALBUM ->{
                if (resultCode == Activity.RESULT_OK) {
                    if (data?.data != null) {
                        try {
                            cameraUtil.setImageUri(data.data)
                            imageAdd(cameraUtil.getImageUri())
                            //cropImage()
                        } catch (e: Exception) {
                        }
                    }
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun imageAdd(uri: Uri?) {
        val view = layoutInflater.inflate(R.layout.diary_image_layout, null) as LinearLayout
        imgArray.add(uri.toString())
        view.diary_img.apply {
            setImageURI(uri)
            clipToOutline = true
        }
        view.del_btn.setOnClickListener {
            imgArray.remove(uri.toString())
            image_layout.removeView(view)
        }
        image_layout.addView(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        val list = mPopupList.filter { popup -> popup.isShowing }
        if(list.isNotEmpty()) list[0].dismiss()
    }

    private fun showKeyboard(){
        keyBoardUtil.showKeyboard()
    }

    private fun hideKeyboard(){
        if (title_et.hasFocus()) keyBoardUtil.hideKeyboard(title_et)
        else keyBoardUtil.hideKeyboard(subtitle_et)
    }

}