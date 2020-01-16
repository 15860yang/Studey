package com.snowman.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class LeakCanaryActivity : AppCompatActivity() {

    init {
        log("LeakCanaryActivity  创建了")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leak_canary)
        Thread {
            while (true) {

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        log("LeakCanaryActivity 销毁了")
    }
}
