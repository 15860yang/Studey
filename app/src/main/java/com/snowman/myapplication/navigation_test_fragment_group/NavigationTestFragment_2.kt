package com.snowman.myapplication.navigation_test_fragment_group

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager

class NavigationTestFragment_2 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val content = TextView(context)
        content.gravity = Gravity.CENTER
        content.text = "我是 NavigationTestFragment_2"
        content.layoutParams = ViewPager.LayoutParams()
        return content
    }
}