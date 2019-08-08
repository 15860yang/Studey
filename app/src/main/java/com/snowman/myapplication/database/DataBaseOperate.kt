package com.snowman.myapplication.database

import android.content.Context

object DataBaseOperate {

    fun init(context: Context) {
        UserDatabase.init(context)
    }

    fun insert(user: User) {
        UserDatabase.INSTANCE.userDao().insert(user)

    }

    fun getAllUsers(): List<User> {
        return UserDatabase.INSTANCE.userDao().getAllUsers()
    }

    fun queryById(id: Int): User {
        return UserDatabase.INSTANCE.userDao().queryById(id)
    }

    fun delete(user: User) {
        UserDatabase.INSTANCE.userDao().delete(user)
    }


}
