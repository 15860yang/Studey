package com.snowman.myapplication.fragment_test

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentAdapter(fm: FragmentManager, private var datalist: Array<Fragment>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int) = datalist[position]

    override fun getCount() = datalist.size

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

}
