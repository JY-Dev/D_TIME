package com.jaeyoung.d_time.utils

import android.content.res.Resources

/**
 * Int to Dp
 */
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()