package com.example.jforceadmin.adapters

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import com.example.jforceadmin.DetailView
import com.example.jforceadmin.R
import com.example.jframetask.util.rooms
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.bookingslayout.view.*

class MainPageAdapter(val context: Context, val room: rooms): Item<ViewHolder>(){
    companion object{
        val IMAGE="IMAGE"
        val TYPE="TYPE"
        val PRICE="PRICE"
        val NAME="NAME"
        val checkin="Checkin"
        val checkout="Checkout"
        val room_no="Room_No"
        val Capacity="Capacity"
    }


    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            Glide.with(context)
                .load(room.Image)
                .into(rooming)
           Name.text="Name :${room.Name}"
           RoomType.text="Type :${room.RoomType}"
            Roomno.text=" Room No :${room.RoomNo}"
            checkout.text="Checkout :${room.CheckOut}"
            checkin.text="CheckIn :${room.CheckIn}"
            noofguest.text="No of Guest :${room.NoOfGuest}"


        }
        viewHolder.itemView.setOnClickListener {
            val intent= Intent(context, DetailView::class.java)
            intent.putExtra(IMAGE,room.Image)
            intent.putExtra(TYPE,room.RoomType)
            intent.putExtra(PRICE,room.Price)
            intent.putExtra(NAME,room.Name)
            intent.putExtra(checkin,room.CheckIn)
            intent.putExtra(checkout,room.CheckOut)
            intent.putExtra(room_no,room.RoomNo)
            intent.putExtra(Capacity,room.NoOfGuest)
            context.startActivity(intent)

        }

    }

    override fun getLayout(): Int {
        return R.layout.bookingslayout
    }

}