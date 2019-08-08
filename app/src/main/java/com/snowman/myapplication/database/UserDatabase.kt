package com.snowman.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        private val DB_NAME = "UserDatabase.db"

        lateinit var INSTANCE: UserDatabase

        fun init(context: Context){
            INSTANCE = Room.databaseBuilder(context, UserDatabase::class.java,
                DB_NAME
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}
