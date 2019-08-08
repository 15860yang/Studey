package com.snowman.remote

import android.os.IBinder
import android.os.Parcel

class Proxy(private val mRemote: IBinder) : IRemoteManager {
    override fun getBookList(): List<Book> {
        val data = Parcel.obtain()
        val reply = Parcel.obtain()
        println("---proxy getBookList")
        val result: List<Book>
        try {
            data.writeInterfaceToken(DESCRIPTOR)
            mRemote.transact(TRANSACTION_getBookList, data, reply, 0)
            reply.readException()
            result = reply.createTypedArrayList(Book.CREATOR) as List<Book>
        } finally {
            reply.recycle()
            data.recycle()
        }
        return result
    }

    override fun addBook(book: Book?) {
        val data = Parcel.obtain()
        val reply = Parcel.obtain()
        println("---proxy addBook")
        try{
            data.writeInterfaceToken(DESCRIPTOR)
            if( book != null){
                data.writeInt(1)
                book.writeToParcel(data,0)
            }else {
                data.writeInt(0)
            }
            mRemote.transact(TRANSACTION_addBook,data,reply,0)
            reply.readException()
        }finally {
            reply.recycle()
            data.recycle()
        }
    }

    override fun asBinder(): IBinder {
        return mRemote
    }

}