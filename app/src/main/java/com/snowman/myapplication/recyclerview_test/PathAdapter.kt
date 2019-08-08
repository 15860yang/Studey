package com.snowman.myapplication.recyclerview_test

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.snowman.myapplication.R

class PathAdapter(private val context:Context,private val dataList:ArrayList<String>) : RecyclerView.Adapter<PathAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        val tv = TextView(context)
        tv.layoutParams = params
        tv.setPadding(30,30,30,30)
        tv.setBackgroundResource(R.drawable.text_board_bg)
        tv.setTextColor(ContextCompat.getColor(context,android.R.color.white))
        return ViewHolder(tv)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView as TextView).text = "     "+dataList[position] + "     "
    }

    class ViewHolder(private val item: View) : RecyclerView.ViewHolder(item)
}