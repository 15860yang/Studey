package com.snowman.myapplication.database

import androidx.room.*

import androidx.room.ForeignKey.CASCADE

@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["index"],
        onDelete = CASCADE
    )], indices = [Index(value = ["index"], unique = true)]
)
class UserInfo(
    @PrimaryKey(autoGenerate = true)
    private val id:Int = 0,

    @ColumnInfo(name = "address")
    private val address: String,

    @ColumnInfo(name = "sex")
    private val sex: Int,

    @ColumnInfo(name = "index")
    private val index:Int
) {
    override fun toString() = ""
}