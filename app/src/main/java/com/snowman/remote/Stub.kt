package com.snowman.remote

import android.os.IBinder
import android.os.Parcel
import com.snowman.remote.Book.CREATOR.createFromParcel

abstract class Stub : android.os.Binder(), IRemoteManager {

    init {
        attachInterface(this, DESCRIPTOR)
    }

    companion object {
        fun asInterface(obj: IBinder): IRemoteManager? {
            var iin = obj.queryLocalInterface("com.snowman.com.snowman.myapplication.remote.IRemoteManager")
            return if (iin != null && iin is IRemoteManager) {
                println("---Stub 同一进程")
                iin
            } else {
                println("---Stub 不同进程")
                Proxy(obj)
            }
        }
    }

    override fun asBinder() = this

    override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
        println("---Stub 有人调方法来了")
        return when (code) {
            INTERFACE_TRANSACTION -> {
                reply!!.writeString(DESCRIPTOR)
                return true
            }
            TRANSACTION_getBookList -> {
                data.enforceInterface(DESCRIPTOR)
                reply!!.writeNoException()
                reply.writeList(getBookList())
                return true
            }
            TRANSACTION_addBook -> {
                data.enforceInterface(DESCRIPTOR)
                this.addBook(if (0 != data.readInt()) createFromParcel(data) else null)
                reply!!.writeNoException()
                return true
            }
            else -> super.onTransact(code, data, reply, flags)
        }
    }
}
