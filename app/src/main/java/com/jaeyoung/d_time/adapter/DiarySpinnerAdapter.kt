package com.jaeyoung.d_time.adapter

import android.content.Context
import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.jaeyoung.d_time.R
import kotlinx.android.synthetic.main.diary_spinner_row.view.*

/**
 * DiarySpinnerAdapter (Weather,Emotion)
 */
class DiarySpinnerAdapter(context: Context, imageArray: TypedArray) : BaseAdapter() {
    val imageTypeArray = imageArray
    val mContext = context
    override fun getView(p0: Int, v: View?, p2: ViewGroup?): View {
        val view = v?:LayoutInflater.from(mContext).inflate(R.layout.diary_spinner_row, null)
        view.image_spinner.setImageDrawable(imageTypeArray.getDrawable(p0))
        return view
    }

    override fun getItem(p0: Int): Any {
        return imageTypeArray
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return imageTypeArray.length()
    }
}