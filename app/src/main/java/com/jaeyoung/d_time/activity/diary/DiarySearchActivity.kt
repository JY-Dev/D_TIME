package com.jaeyoung.d_time.activity.diary

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.activity.BaseActivity
import com.jaeyoung.d_time.adapter.diary.DiaryAdapter
import com.jaeyoung.d_time.viewModel.diary.DiaryViewModel
import kotlinx.android.synthetic.main.activity_diary_search.*

class DiarySearchActivity : BaseActivity() {
    lateinit var diaryAdapter : DiaryAdapter
    lateinit var diaryViewModel: DiaryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_search)
        toolBarInit("SEARCH")
        layoutInit()
        viewModelInit()
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