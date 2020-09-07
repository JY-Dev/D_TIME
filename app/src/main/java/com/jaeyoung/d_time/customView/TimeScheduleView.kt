package com.jaeyoung.d_time.customView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jaeyoung.d_time.activity.timetable.TimeTableActivity
import com.jaeyoung.d_time.callback.TimeScheduleSelCallback
import com.jaeyoung.d_time.room.timetable.TimeTableData
import com.jaeyoung.d_time.utils.getTimeData
import kotlin.math.*

class TimeScheduleView : ViewGroup {
    private var screenWidth = 0
    private var screenHeight = 0
    private val CENTER_ANGLE = 270f
    private val data = mutableListOf<Schedule>()
    private var activity: TimeTableActivity? = null
    private lateinit var timeScheduleSelCallback: TimeScheduleSelCallback
    private var curSchedule: Schedule? = null

    constructor(context: Context) : super(context) {
        init()
        setWillNotDraw(false)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
        setWillNotDraw(false)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setWillNotDraw(false)
    }

    fun setActivity(context: TimeTableActivity) {
        activity = context
    }

    fun setCallback(timeScheduleSelCallback: TimeScheduleSelCallback) {
        this.timeScheduleSelCallback = timeScheduleSelCallback
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    if (backgroundClickable(motionEvent.x, motionEvent.y)) {
                        val pX = getPositionX(motionEvent.x)
                        val pY = getPositionY(motionEvent.y)
                        val quad = getQuadrant(pX, pY)
                        val angle = getAngle(quad, abs(pX), abs(pY))
                        data.forEachIndexed { index, schedule ->
                            if (itemTouchCondition(
                                    angle,
                                    schedule.startAngle,
                                    schedule.endAngle
                                )
                            ) {
                                timeScheduleSelCallback.select(schedule.id)
                            }
                        }
                    }
                }
            }
            true
        }
    }

    fun backgroundClickable(x: Float, y: Float): Boolean {
        val absX = abs(x)
        val absY = abs(y)
        val height = if (absY > screenWidth / 2) absY - (screenWidth / 2)
        else (screenWidth / 2) - absY
        val width = if (absX > screenWidth / 2) absX - (screenWidth / 2)
        else (screenWidth / 2) - absX
        return sqrt((width.toDouble().pow(2.0) + height.toDouble().pow(2.0))) < screenWidth / 2
    }

    fun itemTouchCondition(
        angle: Float,
        startAngle: Float,
        endAngle: Float
    ): Boolean {
        return (angle > startAngle && angle < endAngle) || (startAngle > endAngle && (angle > startAngle || (angle >= 0 && angle < endAngle)))
    }

    fun itemAddCondition(
        angle: Float,
        startAngle: Float,
        endAngle: Float
    ): Boolean {
        return (angle in startAngle..endAngle) || (startAngle >= endAngle && (angle >= startAngle || (angle >= 0 && angle <= endAngle)))
    }


    fun getAngle(quad: Int, width: Float, height: Float): Float {
        val angle = atan2(width.toDouble(), height.toDouble()) * 180 / PI
        return when (quad) {
            1 -> {
                angle.toFloat()
            }
            2 -> {
                360 - angle.toFloat()

            }
            3 -> {
                180 + angle.toFloat()
            }
            4 -> {
                180 - angle.toFloat()
            }
            else -> 0f
        }
    }

    fun getQuadrant(x: Float, y: Float): Int {
        return if (x > 0 && y > 0) 1
        else if (x < 0 && y > 0) 2
        else if (x < 0 && y < 0) 3
        else 4
    }

    fun getPositionX(p0: Float): Float {
        return if (p0 < screenWidth / 2) p0 - (screenWidth / 2)
        else p0 - (screenWidth / 2)
    }

    fun getPositionY(p0: Float): Float {
        return if (p0 < screenWidth / 2) -(p0 - (screenWidth / 2))
        else -(p0 - (screenWidth / 2))
    }

    fun getTimeAngle(hour: Int, min: Int): Float {
        return (hour * 60 + min) / 1440f * 360f
    }

    fun curItem(timeData: TimeData, targetId: Long): Boolean {
        val startAngle = getTimeAngle(timeData.startHour, timeData.startMin)
        val endAngle = getTimeAngle(timeData.endHour, timeData.endMin)
        var checkFlag = true
        data.forEach {
            /*if (((startAngle > it.startAngle && startAngle < it.endAngle) || (endAngle > it.startAngle && endAngle < it.endAngle))||startAngle==it.startAngle) {
                checkFlag = false
            }*/
            if (it.startAngle == startAngle && endAngle == it.endAngle) {
                checkFlag = false
            }
            if (startAngle < it.startAngle && endAngle > it.endAngle)
                checkFlag = false
            if (startAngle > endAngle) {
                if (it.startAngle < it.endAngle) {
                    if (it.startAngle < endAngle && it.endAngle < endAngle)
                        checkFlag = false
                }
            }

            if (it.startAngle < it.endAngle) {
                if (((startAngle >= it.startAngle && startAngle < it.endAngle) || (endAngle > it.startAngle && endAngle <= it.endAngle))) {
                    checkFlag = false
                }
            } else {
                if ((((startAngle >= it.startAngle && startAngle <= 360) || (startAngle > 0 && startAngle < it.endAngle)) || ((endAngle >= 0 && endAngle <= it.endAngle) || (endAngle > it.startAngle && endAngle < 360)))) {
                    checkFlag = false
                }
            }

            if (targetId == it.id && (it.startAngle == startAngle && endAngle == it.endAngle)) checkFlag =
                true
            // 2020-08-31 추가
        }
        if (startAngle > endAngle) checkFlag = false
        return checkFlag && startAngle != endAngle


    }

    fun setItem(timetableList: MutableList<TimeTableData>) {
        timetableList.forEach {
            val timeData = getTimeData(it.timeData)
            val startAngle = getTimeAngle(timeData.startHour, timeData.startMin)
            val endAngle = getTimeAngle(timeData.endHour, timeData.endMin)
            val wholeAngle =
                if (startAngle > endAngle) 360 - startAngle + endAngle else endAngle - startAngle
            data.add(
                Schedule(
                    startAngle,
                    endAngle,
                    wholeAngle,
                    it.event,
                    it.color,
                    it.id
                )
            )
        }
        invalidate()
    }

    fun clearView() {
        data.clear()
        removeAllViews()
    }

    fun curItem(id : Long){
        data.forEach {
            if(it.id==id) curSchedule = it
        }
        invalidate()
    }

    fun clearSel(){
        curSchedule = null
        invalidate()
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        screenWidth = MeasureSpec.getSize(widthMeasureSpec)
        screenHeight = screenWidth
        setMeasuredDimension(screenWidth, screenHeight)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val pnt = Paint()
        val linePnt = Paint()

        pnt.apply {
            strokeWidth = 6f
            color = Color.parseColor("#FF0000")
            style = Paint.Style.FILL
        }

        linePnt.apply {
            strokeWidth = 3f
            color = Color.parseColor("#000000")
            style = Paint.Style.STROKE
        }
        val rect = RectF()
        rect.set(0f, 0f, screenWidth.toFloat(), screenHeight.toFloat())
        data.forEach {
            pnt.color = Color.parseColor(it.color)
            canvas?.drawArc(rect, CENTER_ANGLE + it.startAngle, it.wholeAngle, true, pnt)
            canvas?.drawArc(rect, CENTER_ANGLE + it.startAngle, it.wholeAngle, true, linePnt)
        }
        if(curSchedule!=null){
            pnt.color = Color.parseColor("#4d000000")
            canvas?.drawArc(rect, CENTER_ANGLE + curSchedule!!.startAngle, curSchedule!!.wholeAngle, true, pnt)
        }
    }

    //pnt.setColor(Color.parseColor("#00FF00"));
    // canvas?.drawArc(rect, CENTER_ANGLE + 180f, 180f, true, pnt)
    data class Schedule(
        val startAngle: Float,
        val endAngle: Float,
        val wholeAngle: Float,
        val event: String,
        val color: String,
        val id: Long
    )

    data class TimeData(
        val startHour: Int,
        val startMin: Int,
        val endHour: Int,
        val endMin: Int
    )
}


