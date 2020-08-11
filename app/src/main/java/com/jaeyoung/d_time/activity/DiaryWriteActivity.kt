package com.jaeyoung.d_time.activity

import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.adapter.DiarySpinnerAdapter
import com.jaeyoung.d_time.utils.DiaryPopup
import com.jaeyoung.d_time.utils.dp
import kotlinx.android.synthetic.main.activity_diary_write.*
import kotlinx.android.synthetic.main.app_toolbar.*

class DiaryWriteActivity : AppCompatActivity() {
    var mPopupList = mutableListOf<PopupWindow>()
    var mPopupViewList = mutableListOf<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_write)
        toolBarInit()
        spinnerInit()
        popupWindowInit()
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

    /**
     * Spinner Init (Weather, Emotion)
     */
    private fun spinnerInit(){
        weather_spinner.adapter = DiarySpinnerAdapter(this,resources.obtainTypedArray(R.array.weather_image))
        emotion_spinner.adapter = DiarySpinnerAdapter(this,resources.obtainTypedArray(R.array.emotion_image))
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
        more_btn.setOnClickListener(popupViewOnClick)
        edit_btn.setOnClickListener(popupViewOnClick)
        whole_view.setOnClickListener {
            mPopupList.filter { popup -> popup.isShowing }[0].dismiss()
            it.visibility = View.GONE
        }
    }

    /**
     * Popup View Set
     */
    private fun popupView(imageArray: TypedArray,idArray:TypedArray) : View{
        val view = layoutInflater.inflate(R.layout.popup_view, null) as LinearLayout
        for(i in 0 until imageArray.length()){
            val imageView = ImageView(this)
            imageView.layoutParams = LinearLayout.LayoutParams(25.dp, 25.dp)
            val lineView = View(this)
            lineView.setBackgroundColor(Color.BLACK)
            val lineViewLP = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1.dp)
            lineViewLP.apply {
                topMargin = 6.dp
                bottomMargin = 6.dp
            }
            lineView.layoutParams = lineViewLP
            imageView.setImageDrawable(imageArray.getDrawable(i))
            imageView.id = idArray.getResourceId(i,0)
            imageView.setOnClickListener(popupItemOnClick)
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
            edit_btn -> mPopupList[DiaryPopup.EDIT.ordinal].showAsDropDown(view,0,10.dp)
            more_btn ->mPopupList[DiaryPopup.MORE.ordinal].showAsDropDown(view,0,10.dp)
        }
        whole_view.visibility = View.VISIBLE
    }

    /**
     * PopupItem OnclickListener
     */
    private val popupItemOnClick = View.OnClickListener { view ->
        when(view.id){
            // Draw Picture
            R.id.brush_btn ->{
                Toast.makeText(this,"Brush",Toast.LENGTH_SHORT).show()
            }
            // Take a Picture
            R.id.photo_btn ->{
                Toast.makeText(this,"Photo",Toast.LENGTH_SHORT).show()
            }
            //Save Diary
            R.id.save_btn -> {
                Toast.makeText(this,"Save",Toast.LENGTH_SHORT).show()
            }
            //Add Bookmark
            R.id.bookmark_btn -> {
                Toast.makeText(this,"BookMark",Toast.LENGTH_SHORT).show()
            }
            //Delete Diary
            R.id.delete_btn -> {
                Toast.makeText(this,"Delete",Toast.LENGTH_SHORT).show()
            }
        }

    }


}