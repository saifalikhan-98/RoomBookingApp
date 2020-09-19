package com.example.jforceadmin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.jforceadmin.adapters.MainPageAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_detail_view.*
import kotlinx.android.synthetic.main.bookingslayout.view.*

class DetailView : AppCompatActivity() {
    companion object{
        var roomType:String?=null
        var Name:String?=null
        var roomsleft:Int?=null
    }
    var getType:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_view)

        getData()
    }





    @SuppressLint("SetTextI18n")
    private fun getData() {
        getType=intent.getStringExtra(MainPageAdapter.TYPE)

        val getImage=intent.getStringExtra(MainPageAdapter.IMAGE)
        val getName=intent.getStringExtra(MainPageAdapter.NAME)
        val getRoomNo=intent.getStringExtra(MainPageAdapter.room_no)
        val getCheckin=intent.getStringExtra(MainPageAdapter.checkin)
        val getCheckout=intent.getStringExtra(MainPageAdapter.checkout)
        val getNmbrs=intent.getStringExtra(MainPageAdapter.Capacity)


        Log.d("details","$getName")


        Glide.with(this)
            .load(getImage)
            .into(roomingdetails)
        Namedetails.text="Name :$getName"
        RoomTypedetails.text="Type :$getType"
        Roomnodetails.text="Room No :$getRoomNo"
        checkoutdetails.text="Checkout :$getCheckout"
        checkindetails.text="CheckIn :$getCheckin"
        noofguestdetails.text="No of Guest :${getNmbrs}"

    }
}