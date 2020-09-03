package com.jaeyoung.d_time.activity.diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.activity.BaseActivity
import com.jaeyoung.d_time.adapter.diary.BookMarkSelAdapter
import com.jaeyoung.d_time.room.diary.bookmark.BookMark
import com.jaeyoung.d_time.room.diary.bookmark.BookMarkData
import com.jaeyoung.d_time.viewModel.diary.BookMarkViewModel
import kotlinx.android.synthetic.main.activity_diary_book_mark_sel.*
import kotlinx.android.synthetic.main.app_toolbar.*

class DiaryBookMarkSelActivity : BaseActivity() {
    lateinit var bookMarkSelAdapter : BookMarkSelAdapter
    lateinit var bookMarkViewModel : BookMarkViewModel
    var bookMarkList = mutableListOf<BookMarkData>()
    var id = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_book_mark_sel)
        toolBarInit("BOOKMARK")
        viewModelInit()
        layoutInit()
    }

    private fun layoutInit(){
        ok_btn.setOnClickListener {
            bookMarkSelAdapter.addList().forEach {
                bookMarkViewModel.insertBookmarkData(BookMarkData(id,it))
            }
            bookMarkSelAdapter.delList().forEach {
                bookMarkViewModel.deleteBookMarkData(id,it)
            }
            finish()
        }
    }

    private fun viewModelInit(){
        val androidViewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        id = intent.getLongExtra("id",0L)
        bookMarkSelAdapter = BookMarkSelAdapter(this)
        bookmark_list.adapter = bookMarkSelAdapter
        bookMarkViewModel = ViewModelProvider(this,androidViewModelFactory).get(
            BookMarkViewModel::class.java)

        bookMarkViewModel.getBookMark()
        bookMarkViewModel.getIdData(id)

        bookMarkViewModel.bookMarkList.observe(this, Observer {
            bookMarkList = it
            bookMarkSelAdapter.setData(it)
        })

        bookMarkViewModel.bookMarkTitle.observe(this, Observer {
            bookMarkSelAdapter.setTitleData(it)
        })


        bookMarkViewModel.status.observe(this, Observer {
            bookMarkViewModel.getBookMark()
        })
    }
}