package com.example.jframetask.Adapter

import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import com.example.jframetask.Activities.DetailView
import com.example.jframetask.R
import com.example.jframetask.util.rooms
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_detail_view.view.*
import kotlinx.android.synthetic.main.mainpagelayout.view.*
import kotlinx.android.synthetic.main.mainpagelayout.view.type
import kotlinx.android.synthetic.main.similarlayout.view.*

class DetailsAdapter(val context: Context, val room: rooms): Item<ViewHolder>(){
    companion object{
        val IMAGE="IMAGE"
        val TYPE="TYPE"
        val PRICE="PRICE"
        val NAME="NAME"

    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            Glide.with(context)
                .load(room.Image)
                .into(roomimg)
            typedet.text = room.Type
            pricedet.text=room.Price

        }
        viewHolder.itemView.setOnClickListener {
            val intent= Intent(context, DetailView::class.java)
            intent.putExtra(IMAGE,room.Image)
            intent.putExtra(TYPE,room.Type)
            intent.putExtra(PRICE,room.Price)
            intent.putExtra(NAME,room.Name)
            context?.startActivity(intent)

        }

    }

    override fun getLayout(): Int {
        return R.layout.similarlayout
    }


}