package com.snowman.remote

import android.os.Parcel
import android.os.Parcelable

class Book(private val bookId: Int, private val bookName: String) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString().toString())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0!!.writeInt(bookId)
        p0.writeString(bookName)
    }

    companion object CREATOR : Parcelable.Creator<Book?> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}