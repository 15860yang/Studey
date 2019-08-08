package com.snowman.myapplication.database

import androidx.room.*
import com.snowman.myapplication.database.User

@Dao
interface UserDao {

    @Insert
    fun insert(vararg users: User)

    //删
    @Delete
    fun delete(vararg users: User)

    //改
    @Update
    fun update(vararg users: User)

    //查
    @Query("SELECT * FROM user")
    fun getAllUsers(): List<User>

    @Query("SELECT * FROM user WHERE id =:userId")
    fun queryById(userId: Int): User
}
