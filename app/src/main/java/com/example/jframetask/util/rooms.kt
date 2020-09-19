package com.example.jframetask.util

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class rooms(
    val Name:String,
    val Image:String,
    val Status:String,
    val Type:String,
    val Price:String,
    val Capacity:String,
    val Check_In:String,
    val Check_Out:String
):Parcelable {
    constructor():this("","","","","","","","")
}