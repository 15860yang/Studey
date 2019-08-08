package com.snowman.remote

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.*

class RemoteService : Service() {

    private val TAG = "---" + RemoteService::class.java.simpleName

    val books: ArrayList<Book> = ArrayList()

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    private var mStub: Stub = object : Stub() {
        override fun getBookList(): List<Book> {
            println("---获取书籍列表")
            return books
        }

        override fun addBook(book: Book?) {
            println("---已经添加了书籍")
            books.add(book!!)
            println("---bookSize = ${books.size}")
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.d(TAG, "有人来了")
        return mStub
    }

}
