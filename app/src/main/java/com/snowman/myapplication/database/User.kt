package com.snowman.myapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var mName: String? = null,

    @ColumnInfo(name = "age")
    var mAge: Int = 0
) {
    override fun toString() = "user{id = $id ,name = $mName ,age = $mAge}"
}
