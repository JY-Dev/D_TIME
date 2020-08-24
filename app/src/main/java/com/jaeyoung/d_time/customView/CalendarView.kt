package jaeyoung.kim.calendarview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.viewModel.calendar.CalendarViewModel
import kotlinx.android.synthetic.main.top_layout.view.*
import java.text.SimpleDateFormat
import java.util.*


class CalendarView : LinearLayout {
    private val dayOfWeek = mutableListOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")
    private var calendarAdapter = CalendarAdapter(context)
    private val cal = Calendar.getInstance()
    private var year = cal.get(Calendar.YEAR)
    private var month = cal.get(Calendar.MONTH)
    private var selCal = Calendar.getInstance()
    private var currentDate = cal
    private var gridView : GridView = GridView(context)
    private var calViewModel : CalendarViewModel? = null
    private var x1 = 0f
    private var x2 = 0f
    lateinit var topDateView : View

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
//        val attrsArray = context.theme.obtainStyledAttributes(attrs, R.styleable.RowView, 0, 0)
//        rowCount = attrsArray.getInt(R.styleable.RowView_rowCount, 3)
//        itemMargin = attrsArray.getInt(R.styleable.RowView_itemMargin, 30)
        init()

    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private fun init() {
        orientation = VERTICAL
        cal.set(year, month, 1, 0, 0, 0)
        selCal.set(year, month, 1, 0, 0, 0)
        addView(createTopDateView())
        addView(createTopView())
        addView(createGridView())
    }

    private fun createTopView(): LinearLayout {
        val l1 = LinearLayout(context)
        val lp = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        l1.orientation = HORIZONTAL
        l1.layoutParams = lp
        l1.weightSum = 7F
        for (i in 0 until dayOfWeek.size) {
            var color = Color.BLACK
            val textView = createTextView(dayOfWeek[i])
            when(i){
                0 -> color = Color.RED
                dayOfWeek.size-1 -> color = Color.BLUE
            }
            textView.setTextColor(color)
            val textFont = ResourcesCompat.getFont(context,R.font.font)
            textView.typeface = textFont
            textView.textSize = 15f
            l1.addView(textView)
        }
        l1.requestLayout()
        return l1
    }

    private fun createTopDateView(): View {
        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        topDateView = inflator.inflate(R.layout.top_layout, null)
        topDateView.date_tv.text = getDate(cal)
        topDateView.pre_btn.setOnClickListener {
            downToMonth()
            topDateView.date_tv.text = getDate(cal)
            calendarAdapter.calUpdate(cal)
        }
        topDateView.next_btn.setOnClickListener {
            upToMonth()
            topDateView.date_tv.text = getDate(cal)
            calendarAdapter.calUpdate(cal)
        }
        return topDateView
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun createGridView(): GridView {
        val lp = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        gridView.setOnItemClickListener { adapterView, view, position, l ->
            if(adapterView.getItemAtPosition(position)!=0){
                selCal.set(year, month, adapterView.getItemAtPosition(position) as Int, 0, 0, 0)
                calViewModel.let {
                    it?.setCal(selCal)
                }
                calendarAdapter.setSelect(position,selCal)
            }

        }
        gridView.apply {
            layoutParams = lp
            numColumns = 7
            adapter = calendarAdapter
            isVerticalScrollBarEnabled = false
            stretchMode = GridView.STRETCH_COLUMN_WIDTH
            //setSelector(R.drawable.list_selector)
            choiceMode = AbsListView.CHOICE_MODE_SINGLE
        }
        gridView.setOnTouchListener { _, event ->
            when(event?.action){
                MotionEvent.ACTION_DOWN -> {
                    x1 = event.x
                }
                MotionEvent.ACTION_UP -> {
                    x2 = event.x
                    val deltaX = x2 - x1
                    if(Math.abs(deltaX) > 150){
                        if(x2>x1) {
                            downToMonth()
                            topDateView.date_tv.text = getDate(cal)
                            calendarAdapter.calUpdate(cal)
                        }
                        else {
                            upToMonth()
                            topDateView.date_tv.text = getDate(cal)
                            calendarAdapter.calUpdate(cal)
                        }
                    }
                }
                else -> {

                }
            }
            false
        }
        return gridView
    }

    fun getCal():Calendar{
        return selCal
    }

    fun setCalViewModel(calendarViewModel: CalendarViewModel){
        calViewModel = calendarViewModel
    }

    private fun createTextView(text: String): TextView {
        val lp = LayoutParams(
            0,
            ViewGroup.LayoutParams.WRAP_CONTENT, 1F
        )
        val textView = TextView(context)
        textView.text = text
        textView.textSize = 18f
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.layoutParams = lp
        return textView
    }

    private fun pixelToDp(value: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value,
            resources.displayMetrics
        ).toInt()
    }

    private fun getDate(cal:Calendar): String {
        val simpleFormat = SimpleDateFormat("MMMM yyyy",Locale.ENGLISH)
        return simpleFormat.format(cal.time)
    }

    private fun getDateFull(cal:Calendar): String {
        val simpleFormat = SimpleDateFormat("yyyy.MM.dd")
        return simpleFormat.format(cal.time)
    }

    private fun downToMonth() {
        month -= 1
        if (month <= 0) {
            month = 12
            year -= 1
        }
        cal.set(year, month, 1, 0, 0, 0)
    }

    private fun upToMonth() {
        month += 1
        if (month > 12) {
            month = 1
            year += 1
        }
        cal.set(year, month, 1, 0, 0, 0)
    }


}