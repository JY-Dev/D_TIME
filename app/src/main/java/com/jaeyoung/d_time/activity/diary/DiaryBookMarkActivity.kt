package com.jaeyoung.d_time.activity.diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.adapter.diary.BookMarkAdapter
import com.jaeyoung.d_time.room.diary.bookmark.BookMark
import com.jaeyoung.d_time.viewModel.diary.BookMarkViewModel
import kotlinx.android.synthetic.main.activity_diary_book_mark.*
import kotlinx.android.synthetic.main.fragment_todo.add_btn

class DiaryBookMarkActivity : AppCompatActivity() {
    lateinit var bookMarkViewModel: BookMarkViewModel
    var bookMarkList = mutableListOf<BookMark>()
    lateinit var bookMarkAdapter : BookMarkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_book_mark)
        viewModelInit()
        layoutInit()

    }

    private fun layoutInit(){

        bookmark_list.adapter = bookMarkAdapter
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
                val bookMarkData = BookMark(bookmark_et.text.toString())
                bookMarkViewModel.inserBookMark(bookMarkData)
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
                    bookMarkViewModel.deleteBookMark(it.bookMark)
                }
                bookMarkAdapter.clearDelList()
            }
        }
        bookMarkViewModel.getBookMark()
    }

    private fun viewModelInit(){
        val androidViewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        bookMarkAdapter = BookMarkAdapter(this)
        bookMarkViewModel = ViewModelProvider(this,androidViewModelFactory).get(
            BookMarkViewModel::class.java)
        bookMarkViewModel.bookMarkTitle.observe(this, Observer {
            bookMarkList = it
            bookMarkAdapter.setData(it)
        })
        bookMarkViewModel.status.observe(this, Observer {
            Toast.makeText(this,"okok",Toast.LENGTH_LONG).show()
            bookMarkViewModel.getBookMark()
        })
    }
}