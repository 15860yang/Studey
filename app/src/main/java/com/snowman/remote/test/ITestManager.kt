package com.snowman.remote.test

import android.os.IBinder
import android.os.IInterface

interface ITestManager :IInterface {

    val DESCRIPTOR: String
        get() = ITestManager::class.java.name
    val FUNCTION_ASYN_INVOKE: Int
        get() = IBinder.FIRST_CALL_TRANSACTION + 1
    val FUNCTION_SYN_INVOKE: Int
        get() = IBinder.FIRST_CALL_TRANSACTION + 2

    fun asynInvoke()

    fun sunInvoke()
}