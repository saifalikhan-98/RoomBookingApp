package com.example.jframetask.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import com.example.jframetask.Activities.DetailView
import com.example.jframetask.Adapter.MainPageAdapter.Companion.IMAGE
import com.example.jframetask.R
import com.example.jframetask.util.rooms
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.mainpagelayout.view.*

class MainPageAdapter(val context: Context,val room:rooms):Item<ViewHolder>(){
    companion object{
        val IMAGE="IMAGE"
        val TYPE="TYPE"
        val PRICE="PRICE"
        val NAME="NAME"
        val CAPACITY="CAPACITY"

    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            Glide.with(context)
                .load(room.Image)
                .into(slideimages)
            type.text = room.Type

        }
        viewHolder.itemView.setOnClickListener {
            val intent=Intent(context,DetailView::class.java)
            intent.putExtra(IMAGE,room.Image)
            intent.putExtra(TYPE,room.Type)
            intent.putExtra(PRICE,room.Price)
            intent.putExtra(NAME,room.Name)
            intent.putExtra(CAPACITY,room.Capacity)
            context?.startActivity(intent)

        }

    }

    override fun getLayout(): Int {
        return R.layout.mainpagelayout
    }


}