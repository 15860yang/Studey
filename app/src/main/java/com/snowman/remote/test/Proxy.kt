package com.snowman.remote.test

import android.os.IBinder

class Proxy(private val mRemote: IBinder) : ITestManager {
    override fun asynInvoke() {

    }

    override fun sunInvoke() {
    }

    override fun asBinder() = mRemote
}