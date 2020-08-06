package jaeyoung.kim.calendarview

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.jaeyoung.d_time.R
import kotlinx.android.synthetic.main.item_layout.view.*
import java.util.*

class CalendarAdapter(context: Context) : BaseAdapter() {
    private val day = mutableListOf<Int>()
    private var mContext : Context = context
    private var lastWeekDay = 0
    private var firstWeekDay = 0
    private var dayOfMonth = 0
    private var DAY_OF_WEEK = 7
    private var selPostion = 0
    private var monthCal = Calendar.getInstance()
    private var selCal = Calendar.getInstance()
    private var checkFlag = false

    init {
        setCal()
    }

    override fun getView(position: Int, view: View?, p2: ViewGroup?): View {
        val inflator = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val mView = view ?: inflator.inflate(R.layout.item_layout,null)
        if(selPostion==position&&checkFlag) mView.setBackgroundResource(R.drawable.item_border)
        else mView.setBackgroundColor(Color.WHITE)
        if(day[position]!=0) mView.day_tv.text = day[position].toString()
        else mView.day_tv.text = ""
        return mView
    }

    override fun getItem(p0: Int): Any {
        return day[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return day.size
    }

    fun calUpdate(cal : Calendar){
        this.monthCal = cal
        setCal()
        notifyDataSetChanged()
    }

    fun setSelect(position: Int,cal:Calendar){
        this.selCal = cal
        selPostion = position
        setCal()
        notifyDataSetChanged()
    }

    private fun setCal(){
        day.clear()
        monthCal.set(Calendar.DATE,1)
        firstWeekDay = monthCal.get(Calendar.DAY_OF_WEEK)-1
        monthCal.set(Calendar.DATE,monthCal.getActualMaximum(Calendar.DAY_OF_MONTH))
        dayOfMonth = monthCal.get(Calendar.DATE)
        lastWeekDay = DAY_OF_WEEK-monthCal.get(Calendar.DAY_OF_WEEK)
        var count = 1
        for(i in 0 until dayOfMonth+firstWeekDay+lastWeekDay){
            if(i>=firstWeekDay&&i<firstWeekDay+monthCal.getActualMaximum(Calendar.DAY_OF_MONTH)){
                day.add(count)
                if(count==selCal.get(Calendar.DATE)) selPostion = i
                checkFlag = selCal.get(Calendar.MONTH)==monthCal.get(Calendar.MONTH)&&selCal.get(Calendar.YEAR)==monthCal.get(Calendar.YEAR)
                count++
            } else day.add(0)
        }
    }
}