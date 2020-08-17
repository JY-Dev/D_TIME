package com.jaeyoung.d_time.fragment

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.activity.diary.DiaryBookMarkActivity
import com.jaeyoung.d_time.activity.diary.DiaryViewActivity
import com.jaeyoung.d_time.activity.diary.DiaryWriteActivity
import com.jaeyoung.d_time.activity.main.MainActivity
import com.jaeyoung.d_time.adapter.diary.DiaryAdapter
import com.jaeyoung.d_time.room.diary.DiaryData
import com.jaeyoung.d_time.viewModel.diary.DiaryViewModel
import kotlinx.android.synthetic.main.fragment_diary.*
import kotlinx.android.synthetic.main.fragment_diary.view.*
import java.text.SimpleDateFormat
import java.util.*


class DiaryFragment(context: Context, application: Application) : Fragment() {
    private val mContext = context
    private val mApplication = application
    private val mainActivity = context as MainActivity
    private val calViewModel = mainActivity.getCalendarViewModel()
    private var cal = Calendar.getInstance()
    lateinit var diaryViewModel: DiaryViewModel
    lateinit var diaryAdapter: DiaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_diary, container, false)
        viewModelInit()
        diaryAdapter = DiaryAdapter(mContext)
        view.diary_list.apply {
            adapter = diaryAdapter
            setOnItemClickListener { _, _, position, _ ->
                val intent = Intent(Intent(mContext, DiaryViewActivity::class.java))
                val item = diaryAdapter.getItem(position) as DiaryData
                intent.putExtra("primary", item.id)
                startActivity(intent)
            }
        }

        val searchViewText : SearchView.SearchAutoComplete  = view.search.findViewById(androidx.appcompat.R.id.search_src_text)
        val myTypeface = ResourcesCompat.getFont(mContext,R.font.font)
        searchViewText.typeface = myTypeface

        view.search_btn.setOnClickListener {
            if(search.visibility == View.GONE) search.visibility = View.VISIBLE
            else {
                search.visibility = View.GONE
                diaryAdapter.filterClear()
                view.search.setQuery("",false)

            }
        }

        view.bookmark_btn.setOnClickListener {
            startActivity(Intent(mContext,DiaryBookMarkActivity::class.java))
        }
        view.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                diaryAdapter.filterSet(newText)
                return false
            }

        })

        calViewModel.calData.observe(this, androidx.lifecycle.Observer {
            cal = it
            mainActivity.dismissCalendarDialog()
            diaryViewModel.getDiaryData(getDateFull(cal))
        })
        view.cal_btn.setOnClickListener {
            mainActivity.showCalendarDialog()
        }
        view.write_btn.setOnClickListener {
            val intent = Intent(Intent(mContext, DiaryWriteActivity::class.java))
            intent.putExtra("date", getDateFull(cal))
            intent.putExtra("way", false)
            startActivity(intent)
        }
        return view
    }

    private fun getDateFull(cal: Calendar): String {
        val simpleFormat = SimpleDateFormat("yyyy.MM.dd")
        return simpleFormat.format(cal.time)
    }

    private fun viewModelInit() {
        val androidViewModelFactory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(mApplication)
        diaryViewModel =
            ViewModelProvider(this, androidViewModelFactory).get(DiaryViewModel::class.java)
        diaryViewModel.diaryList.observe(this, androidx.lifecycle.Observer {
            diaryAdapter.setDiaryData(it)

        })
    }

    override fun onResume() {
        diaryViewModel.getDiaryData(getDateFull(cal))
        super.onResume()

    }
}