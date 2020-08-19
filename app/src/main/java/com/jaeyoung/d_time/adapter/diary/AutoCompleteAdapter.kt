package com.jaeyoung.d_time.adapter.diary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.jaeyoung.d_time.R
import com.jaeyoung.d_time.room.diary.DiaryData
import kotlinx.android.synthetic.main.diary_item.view.*

@Suppress("UNCHECKED_CAST")
class AutoCompleteAdapter(context: Context, diaryData:MutableList<DiaryData>) : ArrayAdapter<DiaryData>(context,0 ,diaryData) {
    private var diaryDataList = diaryData
    private val mContext = context

    override fun getFilter(): Filter {
        return diaryFilter
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView?:LayoutInflater.from(mContext).inflate(R.layout.diary_item,parent,false)
        view.title_tv.text = getItem(position)?.title
        return view
    }

    private val diaryFilter = object : Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            val suggestions  = mutableListOf<DiaryData>()
            if(constraint==null|| constraint.isEmpty()) suggestions.addAll(diaryDataList)
            else {
                val filterPattern = constraint.toString()
                diaryDataList.forEach {
                    if(it.title.contains(filterPattern)) suggestions.add(it)
                }
            }
            results.values = suggestions
            results.count = suggestions.size
            return results
        }


        override fun publishResults(p0: CharSequence?, results: FilterResults?) {
            clear()
            addAll(results?.values as MutableList<DiaryData>)
        }

    }

    fun setData(diaryData: MutableList<DiaryData>){
        diaryDataList = diaryData
        notifyDataSetChanged()
    }
}