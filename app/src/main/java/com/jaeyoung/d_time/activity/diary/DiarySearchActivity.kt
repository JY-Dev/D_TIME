package com.jaeyoung.d_time.activity.diary

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.adapter.diary.DiaryAdapter
import com.jaeyoung.d_time.viewModel.diary.DiaryViewModel
import kotlinx.android.synthetic.main.activity_diary_search.*
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.fragment_diary.search

class DiarySearchActivity : AppCompatActivity() {
    lateinit var diaryAdapter : DiaryAdapter
    lateinit var diaryViewModel: DiaryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_search)
        toolBarInit()
        layoutInit()
        viewModelInit()
    }

    /**
     * Toolbar Init
     */
    private fun toolBarInit(){
        setSupportActionBar(app_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_title.text = "SEARCH"
        back_btn.visibility = View.VISIBLE
        back_btn.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                finish()
            }
        }
    }

    private fun layoutInit(){
        diaryAdapter = DiaryAdapter(this)
        search_list.adapter = diaryAdapter
        search_list.let {
            it.adapter = diaryAdapter
            it.setOnItemClickListener { _, _, position, _ ->
                val intent = Intent(this,DiaryViewActivity::class.java)
                intent.putExtra("primary",diaryAdapter.getItem(position).id)
                startActivity(intent)
            }
        }
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                diaryAdapter.filterSet(newText)
                return false
            }
        })
    }

    private fun viewModelInit(){
        val androidViewModelFactory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        diaryViewModel =
            ViewModelProvider(this, androidViewModelFactory).get(DiaryViewModel::class.java)
        diaryViewModel.diaryAllList.observe(this, androidx.lifecycle.Observer {
            diaryAdapter.setDiaryData(it)
        })

    }

    override fun onResume() {
        diaryViewModel.getAllDiaryData()
        super.onResume()
    }
}