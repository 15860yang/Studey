package com.snowman.remote

import android.os.Binder
import android.os.IBinder

open interface IRemoteManager : android.os.IInterface {

    val DESCRIPTOR: String
        get() = "com.snowman.com.snowman.myapplication.remote.IRemoteManager"
    val TRANSACTION_getBookList: Int
        get() = IBinder.FIRST_CALL_TRANSACTION + 0
    val TRANSACTION_addBook: Int
        get() = IBinder.FIRST_CALL_TRANSACTION + 1

    fun getBookList(): List<Book>?
    fun addBook(book: Book?)

    override fun asBinder(): IBinder {
        return Binder()
    }
}



