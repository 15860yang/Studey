package com.snowman.myapplication

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Field
import java.lang.reflect.Method
import com.github.johnpersano.supertoasts.library.Style.ANIMATIONS_POP
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils
import com.github.johnpersano.supertoasts.library.Style.FRAME_LOLLIPOP
import com.github.johnpersano.supertoasts.library.Style.DURATION_LONG
import android.R
import android.graphics.Color
import com.github.johnpersano.supertoasts.library.Style
import com.github.johnpersano.supertoasts.library.Style.TYPE_BUTTON
import com.github.johnpersano.supertoasts.library.SuperActivityToast


class MainActivity : AppCompatActivity() {

    companion object {
        private var i = 1
        private val TAG = "---" + MainActivity::class.java.simpleName
    }

    private val mToast: Toast by lazy {
        Toast.makeText(this, "", Toast.LENGTH_SHORT)
    }

    @SuppressLint("PrivateApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        val clazz = Class.forName("android.widget.Toast\$TN")
        val fields = clazz.declaredFields
        val field = clazz.getField("mHandler")
        field.isAccessible = false
        val mToast_TN_mHandler = field.get(mToast) as Handler
        val hand = Class.forName("android.os.Handler")
        val mFieldmCallBack = hand.getDeclaredField("mCallback")
        mFieldmCallBack.isAccessible = false
        val get = mFieldmCallBack.get(mToast_TN_mHandler) as Handler.Callback
        mFieldmCallBack.set(mToast_TN_mHandler, TCallBack(get))
        */
        mCancelToast.setOnClickListener {
            mToast.cancel()
        }

        mShowToast.setOnClickListener {
            val string = "这是Toast ${i++}"

            Thread.sleep(100)
            mToast.setText(string)
            mToast.show()
        }

    }

    inner class TCallBack(val obj: Handler.Callback) : Handler.Callback {
        override fun handleMessage(p0: Message): Boolean {
            return obj.handleMessage(p0)
        }

    }


}
