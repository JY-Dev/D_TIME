package com.jaeyoung.d_time.activity.diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.adapter.diary.BookMarkAdapter
import com.jaeyoung.d_time.room.diary.bookmark.BookMark
import com.jaeyoung.d_time.room.diary.bookmark.BookMarkData
import com.jaeyoung.d_time.viewModel.diary.BookMarkViewModel
import kotlinx.android.synthetic.main.activity_diary_book_mark.*
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.fragment_todo.add_btn

class DiaryBookMarkActivity : AppCompatActivity() {
    lateinit var bookMarkViewModel: BookMarkViewModel
    var bookMarkList = mutableListOf<BookMark>()
    var bookMarkTitleList = mutableListOf<String>()
    lateinit var bookMarkAdapter : BookMarkAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_book_mark)
        toolBarInit()
        viewModelInit()
        layoutInit()
    }

    /**
     * Toolbar Init
     */
    private fun toolBarInit(){
        setSupportActionBar(app_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_title.text = "BOOKMARK"
        back_btn.visibility = View.VISIBLE
        back_btn.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                finish()
            }
        }
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
                if(!bookMarkTitleList.contains(bookmark_et.text.toString())){
                    val bookMarkData = BookMark(bookmark_et.text.toString())
                    bookMarkViewModel.inserBookMark(bookMarkData)
                    bookmark_et.text.clear()
                    Toast.makeText(this,"Bookmark Added",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,"Already Exists",Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this,"BookMark Is Empty",Toast.LENGTH_SHORT).show()
            }
        }

        del_btn.setOnClickListener {
            if(bookMarkAdapter.deleteFlag()) {
                add_btn.visibility = View.GONE
                Toast.makeText(this,"Delete Mode",Toast.LENGTH_SHORT).show()
            }
            else {
                add_btn.visibility = View.VISIBLE
                bookMarkAdapter.removeList()
                Toast.makeText(this,"Delete Complete",Toast.LENGTH_SHORT).show()
            }
        }
        bookmark_list.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this,DiaryBookMarkDetailActivity::class.java)
            intent.putExtra("bookmark",bookMarkTitleList[position])
            startActivity(intent)
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
            it.filter { !bookMarkTitleList.contains(it.bookMark) }.forEach {
                bookMarkTitleList.add(it.bookMark)
            }
            bookMarkAdapter.setData(it)
        })

        bookMarkViewModel.status.observe(this, Observer {
            bookMarkViewModel.getBookMark()
        })
    }

    fun delData(bookMark : String){
        bookMarkViewModel.deleteBookMark(bookMark)
    }
}