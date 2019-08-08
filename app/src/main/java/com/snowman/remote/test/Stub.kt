package com.snowman.remote.test

import android.os.Binder
import android.os.IBinder
import android.os.Parcel

abstract class Stub : Binder(), ITestManager {

    companion object {
        fun asInterface(obj: IBinder): ITestManager {
            val lin = obj.queryLocalInterface(ITestManager::class.java.name)
            return if (lin != null && lin is ITestManager) lin else Proxy(obj)
        }
    }

    override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
        return when (code) {
            INTERFACE_TRANSACTION->{
                data.writeString(DESCRIPTOR)
                return true
            }

            FUNCTION_SYN_INVOKE->{
                data.enforceInterface(DESCRIPTOR)

                return true
            }

            FUNCTION_ASYN_INVOKE->{
                data.enforceInterface(DESCRIPTOR)

                return true
            }

            else -> super.onTransact(code, data, reply, flags)

        }


    }


    override fun asBinder() = this

}