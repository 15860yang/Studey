package com.snowman.myapplication

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = "---" + MainActivity::class.java.simpleName
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goToLeakActivityBt.setOnClickListener {
            startActivity(Intent(this, LeakCanaryActivity::class.java))
        }

        showContextName.setOnClickListener {
            log(TestManager.getInstance(this)!!.context::class.java.canonicalName!!)
        }

        changeInputKeyBod.setOnClickListener {
            window.currentFocus?.clearFocus() ?: inputEdit.requestFocus()
        }

        hideKeyBod.setOnClickListener {
            window.currentFocus?.clearFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(
                window.decorView.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }

        showKeyBod.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputEdit.requestFocus()
            imm.showSoftInput(inputEdit, InputMethodManager.SHOW_IMPLICIT)
        }
        Looper.getMainLooper().queue.addIdleHandler {
            log(showKeyBod.windowToken.toString())
            log(hideKeyBod.windowToken.toString())
            return@addIdleHandler false
        }
    }

    override fun onRestart() {
        super.onRestart()
        log("MainActivity onRestart")
    }

}
