package com.snowman.myapplication.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface UserInfoDao {


    @Query("SELECT * FROM userinfo")
    fun getAllDepartment(): List<UserInfo>

    //使用内连接查询
    @Query("SELECT `index`,name  from userinfo INNER JOIN userinfo ON user.id =UserInfo.`index`")
    fun getDepartmentFromCompany(): List<UserInfo>


}