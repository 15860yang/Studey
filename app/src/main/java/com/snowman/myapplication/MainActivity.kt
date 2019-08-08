package com.snowman.myapplication

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Path
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.snowman.myapplication.recyclerview_test.PathAdapter
import com.snowman.myapplication.recyclerview_test.pathmanager.PathLayoutManager
import com.snowman.remote.IRemoteManager
import com.snowman.remote.Stub
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = "---" + MainActivity::class.java.simpleName
    }

    var operateBook: IRemoteManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        doTestPathManager()
    }

    private fun tryPathManager(){
        val path = Path()
        path.moveTo(500f,0f)
        path.rLineTo(0f,2000f)
        val layoutManager =
            PathLayoutManager(path, mItemOffset = 250)
        main_rv.layoutManager = layoutManager
        val date = initDate(20)
        val adapter = PathAdapter(this,date)
        main_rv.adapter = adapter
    }

    fun doTestPathManager(){
        val p = Path()
        p.moveTo(500f,0f)
        p.rLineTo(0f,2000f)
        main_auxiliaryView.setPath(p)
        tryPathManager()
    }

    private fun initDate(i: Int): ArrayList<String> {
        val res = ArrayList<String>()
        for (a in 0 until i){
            res.add("$a 号")
        }
        return res
    }

//    override fun onSupportNavigateUp() = findNavController(this, R.id.my_nav_host_fragment).navigateUp()

    private fun doRemote() {
        println("---version = " + android.os.Build.VERSION.SDK_INT)

        val intent = Intent()
        intent.component = ComponentName("com.snowman.myapplication", "com.snowman.remote.RemoteService")
        startService(intent)

        bindService(intent, connection, 0)
    }

    val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "回来了")
            operateBook = Stub.asInterface(service!!)!!
        }

        override fun onServiceDisconnected(p0: ComponentName?) {

        }
    }
}
