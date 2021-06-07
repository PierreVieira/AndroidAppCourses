package com.example.listmaker.frontend.lists

import android.os.Parcel
import android.os.Parcelable

class TaskList(var name: String, val tasks: MutableList<String> = mutableListOf()) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.createStringArrayList()!!
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeStringList(tasks)
    }

    companion object CREATOR : Parcelable.Creator<TaskList> {
        override fun createFromParcel(parcel: Parcel): TaskList = TaskList(parcel)
        override fun newArray(size: Int): Array<TaskList?> = arrayOfNulls(size)
    }

}