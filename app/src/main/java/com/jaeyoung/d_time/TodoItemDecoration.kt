package com.jaeyoung.d_time

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class TodoItemDecoration(context : Context) : RecyclerView.ItemDecoration() {
    private var mActivity = context

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        val dp = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            4f,
            mActivity.resources.displayMetrics
        ).toInt()
        outRect.bottom = dp
        outRect.top = dp
    }
}