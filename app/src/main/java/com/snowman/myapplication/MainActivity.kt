package com.snowman.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = "---" + MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mMainClock.addPointer(object : ClockPointer(200) {
            override fun changeAngle(angle: Int) = angle + 20
        })
        mMainClock.addPointer(object : ClockPointer(100) {
            var index = 1
            override fun changeAngle(angle: Int): Int {
                return if (index > 10) {
                    index = 1
                    angle + 10
                } else {
                    index++
                    angle
                }
            }
        })
    }

}
