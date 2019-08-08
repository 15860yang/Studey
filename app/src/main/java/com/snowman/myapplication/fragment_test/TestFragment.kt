package com.snowman.myapplication.fragment_test

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager

class TestFragment(private val name: String) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val content = TextView(context)
        content.gravity = Gravity.CENTER
        content.text = "我是 $name"
        content.layoutParams = ViewPager.LayoutParams()
        return content
    }
}