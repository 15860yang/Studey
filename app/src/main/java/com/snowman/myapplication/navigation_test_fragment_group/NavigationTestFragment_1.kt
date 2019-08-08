package com.snowman.myapplication.navigation_test_fragment_group

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.snowman.myapplication.R

class NavigationTestFragment_1 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val content = TextView(context)
        content.gravity = Gravity.CENTER
        content.text = "我是 NavigationTestFragment_1"
        content.layoutParams = ViewPager.LayoutParams()


        content.setOnClickListener {
            //点击跳转page2
            Navigation.findNavController(it).navigate(R.id.action_page2)
        }


        return content
    }
}