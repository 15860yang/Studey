package com.snowman.myapplication

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.snowman.myapplication.database.DataBaseOperate
import com.snowman.myapplication.database.User
import com.snowman.remote.Book
import com.snowman.remote.IRemoteManager
import com.snowman.remote.Stub
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        private val TAG = "---" + MainActivity::class.java.simpleName
    }

    var operateBook: IRemoteManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DataBaseOperate.init(application)

//        DataBaseOperate.insert(User(mName = "孙悟空",mAge = 21))
//        DataBaseOperate.insert(User(mName = "猪八戒",mAge = 13))
//        DataBaseOperate.insert(User(mName = "唐僧"  ,mAge = 33))

        DataBaseOperate.delete(User(mName = "孙悟空", mAge = 21, id = 1))

        val users = DataBaseOperate.getAllUsers()
        for (u in users) {
            println(u)
        }

    }

    private fun doRemote() {
        println("---version = " + android.os.Build.VERSION.SDK_INT)

        val intent = Intent()
        intent.component = ComponentName("com.snowman.myapplication", "com.snowman.remote.RemoteService")
        startService(intent)

        bindService(intent, connection, 0)

        mMainAddBook.setOnClickListener {
            if (operateBook == null) return@setOnClickListener
            operateBook!!.addBook(Book(1, "222"))
        }

        mMainGetBooks.setOnClickListener {
            if (operateBook == null) return@setOnClickListener
            println("--- bookSize = ${operateBook!!.getBookList()!!.size}")
        }
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
