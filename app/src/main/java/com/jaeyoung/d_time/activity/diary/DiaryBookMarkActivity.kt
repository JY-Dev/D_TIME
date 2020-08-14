package com.jaeyoung.d_time.activity.diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.adapter.diary.BookMarkAdapter
import com.jaeyoung.d_time.room.diary.bookmark.BookMarkData
import com.jaeyoung.d_time.viewModel.diary.BookMarkViewModel
import kotlinx.android.synthetic.main.activity_diary_book_mark.*
import kotlinx.android.synthetic.main.fragment_todo.add_btn

class DiaryBookMarkActivity : AppCompatActivity() {
    lateinit var bookMarkViewModel: BookMarkViewModel
    var bookMarkList = mutableListOf<BookMarkData>()
    lateinit var bookMarkAdapter : BookMarkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_book_mark)
        layoutInit()
        viewModelInit()
    }

    private fun layoutInit(){
        bookMarkAdapter = BookMarkAdapter(this)
        add_btn.setOnClickListener {
            when(add_bookmark_view.visibility){
                View.VISIBLE -> {
                    bookmark_et.text.clear()
                    add_bookmark_view.visibility = View.GONE
                }
                else -> add_bookmark_view.visibility = View.VISIBLE
            }
        }
        add_bookmark_btn.setOnClickListener {
            if(bookmark_et.text.isNotEmpty()){
                val bookMarkData = BookMarkData(bookmark_et.text.toString(), mutableListOf())
                bookMarkAdapter.addData(bookMarkData)
                bookMarkViewModel.insertData(bookMarkData)
                bookmark_et.text.clear()
            } else {
                Toast.makeText(this,"Input BookMark",Toast.LENGTH_SHORT).show()
            }
        }
        del_btn.setOnClickListener {
            if(bookMarkAdapter.deleteFlag()) add_btn.visibility = View.GONE
            else {
                add_btn.visibility = View.VISIBLE
                bookMarkAdapter.removeList()
                bookMarkList = bookMarkAdapter.getData()
                bookMarkList.forEach {
                    bookMarkViewModel.deleteData(it.bookMark)
                }
            }
        }
    }

    private fun viewModelInit(){
        val androidViewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        bookMarkViewModel = ViewModelProvider(this,androidViewModelFactory).get(
            BookMarkViewModel::class.java)
        bookMarkViewModel.bookMarkList.observe(this, Observer {
            bookMarkList = it
            bookMarkAdapter.setData(it)
        })
    }
}