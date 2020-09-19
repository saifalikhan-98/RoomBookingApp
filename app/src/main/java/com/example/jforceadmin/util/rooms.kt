package com.example.jframetask.util

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class rooms(
    val Name:String,
    val Image:String,
    val Status:String,
    val RoomType:String,
    val RoomNo:String,
    val NoOfGuest:String,
    val Price:String,
    val CheckIn:String,
    val CheckOut:String
):Parcelable {
    constructor():this("","","","","","","","","")
}