package com.jaeyoung.d_time.utils

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jaeyoung.d_time.customView.TimeScheduleView

val gsonType: TypeToken<TimeScheduleView.TimeData> = object : TypeToken<TimeScheduleView.TimeData>() {}
val gson = GsonBuilder().create()

fun getTime(hour:Int,min:Int) : String{
    return getTimeString(hour) + ":" + getTimeString(min)
}

fun getTimeString(time : Int) : String{
    return  if (time < 10) "0$time" else time.toString()
}

fun getTimeData(timeData : String) : TimeScheduleView.TimeData{
    return gson.fromJson(timeData, gsonType.type) as TimeScheduleView.TimeData
}