package com.snowman.myapplication

import android.content.Context

class TestManager private constructor(var context: Context) {

    companion object {
        private var instance: TestManager? = null

        fun getInstance(context: Context): TestManager? {
            if (instance == null) {
                instance = TestManager(context)
            }
            return instance
        }
    }
}