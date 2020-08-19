package com.jaeyoung.d_time.activity.diary

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.adapter.diary.BookMarkDetailAdapter
import com.jaeyoung.d_time.dialog.CalendarDialog
import com.jaeyoung.d_time.room.diary.bookmark.BookMarkData
import com.jaeyoung.d_time.viewModel.calendar.CalendarViewModel
import com.jaeyoung.d_time.viewModel.diary.BookMarkViewModel
import com.jaeyoung.d_time.viewModel.diary.DiaryViewModel
import kotlinx.android.synthetic.main.activity_diary_book_mark.bookmark_list
import kotlinx.android.synthetic.main.activity_diary_book_mark_detail.*
import kotlinx.android.synthetic.main.app_toolbar.*
import java.text.SimpleDateFormat
import java.util.*

class DiaryBookMarkDetailActivity : AppCompatActivity() {
    lateinit var bookMarkViewModel: BookMarkViewModel
    var bookMarkList = mutableListOf<BookMarkData>()
    lateinit var bookMarkDetailAdapter : BookMarkDetailAdapter
    lateinit var diaryViewModel: DiaryViewModel
    private lateinit var calendarViewModel: CalendarViewModel
    private lateinit var calendarDialog: CalendarDialog
    private var idQueue = LinkedList<Long>()
    var bookMark = ""
    private var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_book_mark_detail)
        toolBarInit()
        viewModelInit()
        layoutInit()
    }

    private fun toolBarInit(){
        setSupportActionBar(app_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        bookMark = intent.extras!!.getString("bookmark","")
        toolbar_title.text = bookMark
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
        bookMarkDetailAdapter = BookMarkDetailAdapter(this)


        calendarViewModel = ViewModelProvider(this,androidViewModelFactory).get(CalendarViewModel::class.java)
        calendarDialog = CalendarDialog(this,calendarViewModel)
        bookmark_list.adapter = bookMarkDetailAdapter

        calendarViewModel.calData.observe(this, Observer {
            cal = it
            dismissCalendarDialog()
            bookMarkViewModel.getBookMarkData(bookMark)
        })

        diaryViewModel = ViewModelProvider(this,androidViewModelFactory).get(DiaryViewModel::class.java)

        bookMarkViewModel = ViewModelProvider(this,androidViewModelFactory).get(
            BookMarkViewModel::class.java)

        bookMarkViewModel.getBookMarkData(bookMark)

        bookMarkViewModel.bookMarkList.observe(this, Observer {
            bookMarkDetailAdapter.clearList()
            bookMarkList = it
            it.forEachIndexed {index , data ->
                if(index>0)idQueue.push(data.id)
                else diaryViewModel.getDiaryOneData(data.id)
            }
        })

        diaryViewModel.diary.observe(this, Observer {

            if(it.date == getDate(cal)){
                bookMarkDetailAdapter.setData(it)
            }
            if(idQueue.isNotEmpty()) {
                val id = idQueue.poll() ?: 0
                diaryViewModel.getDiaryOneData(id)
            }
        })

        bookMarkViewModel.status.observe(this, Observer {
            bookMarkViewModel.getBookMarkData(bookMark)
        })

        bookmark_list.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this,DiaryViewActivity::class.java)
            intent.putExtra("primary",bookMarkDetailAdapter.getData()[position].id)
            startActivity(intent)
        }
    }

    private fun layoutInit(){
        del_btn.setOnClickListener {
            if(bookMarkDetailAdapter.deleteFlag()) {
                cal_btn.visibility = View.GONE
                Toast.makeText(this,"Delete Mode", Toast.LENGTH_SHORT).show()
            }
            else {
                cal_btn.visibility = View.VISIBLE
                bookMarkDetailAdapter.removeList()
                Toast.makeText(this,"Delete Complete", Toast.LENGTH_SHORT).show()
            }
        }
        cal_btn.setOnClickListener {
            showCalendarDialog()
        }
    }

    fun delData(id: Long){
        bookMarkViewModel.deleteBookMarkData(id,bookMark)
    }

    private fun getDate(cal: Calendar): String {
        val simpleFormat = SimpleDateFormat("yyyy.MM.dd")
        return simpleFormat.format(cal.time)
    }

    fun showCalendarDialog(){
        calendarDialog.show()
    }

    fun dismissCalendarDialog(){
        calendarDialog.dismiss()
    }

    override fun onResume() {
        bookMarkViewModel.getBookMarkData(bookMark)
        super.onResume()
    }
}