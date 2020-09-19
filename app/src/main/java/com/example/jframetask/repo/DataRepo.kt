package com.example.jframetask.repo

import android.content.Context
import com.example.jframetask.Activities.MainActivity
import com.example.jframetask.Adapter.DetailsAdapter
import com.example.jframetask.Adapter.MainPageAdapter
import com.example.jframetask.util.rooms
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class DataRepo {

    companion object {
        val adapter = GroupAdapter<ViewHolder>()

        fun getDataMainPage(context:Context) {

            val db = FirebaseDatabase.getInstance().getReference()
            db.child("Delux/").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val getData = it.getValue(rooms::class.java)!!
                        adapter.add(MainPageAdapter(context,getData))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
            db.child("Standard/").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val getData = it.getValue(rooms::class.java)!!
                        adapter.add(MainPageAdapter(context,getData))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
            db.child("Luxury/").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val getData = it.getValue(rooms::class.java)!!
                        adapter.add(MainPageAdapter(context,getData))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

        }
        fun luxuryonly(context: Context){
            val db = FirebaseDatabase.getInstance().getReference()
            db.child("Luxury/").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val getData = it.getValue(rooms::class.java)!!
                        adapter.add(MainPageAdapter(context,getData))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

        }
        fun standardonly(context: Context){
            val db = FirebaseDatabase.getInstance().getReference()
            db.child("Standard/").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val getData = it.getValue(rooms::class.java)!!
                        adapter.add(MainPageAdapter(context,getData))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

        }
        fun deluxonly(context: Context){
            val db = FirebaseDatabase.getInstance().getReference()
            db.child("Delux/").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val getData = it.getValue(rooms::class.java)!!
                        adapter.add(MainPageAdapter(context,getData))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

        }

        fun detailsStandard(context: Context){
            val db = FirebaseDatabase.getInstance().getReference()
            db.child("Standard/").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val getData = it.getValue(rooms::class.java)!!
                        adapter.add(DetailsAdapter(context,getData))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        }
        fun detailsDelux(context: Context){
            val db = FirebaseDatabase.getInstance().getReference()
            db.child("Delux/").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val getData = it.getValue(rooms::class.java)!!
                        adapter.add(DetailsAdapter(context,getData))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

        }
        fun detailsLuxury(context: Context){
            val db = FirebaseDatabase.getInstance().getReference()
            db.child("Luxury/").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val getData = it.getValue(rooms::class.java)!!
                        adapter.add(DetailsAdapter(context,getData))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        }
        fun datefilterData(context: Context){

            val deluxrooms=MainActivity.AvailableRoomsDelux!!.toInt()
            val Stanrooms=MainActivity.AvailableRoomsStn!!.toInt()
            val luxrooms=MainActivity.AvailableRoomsLux!!.toInt()


            when{
                deluxrooms==0->{
                    adapter.clear()
                    detailsStandard(context)
                    detailsLuxury(context)
                }
                Stanrooms==0->{
                    adapter.clear()
                    detailsLuxury(context)
                    detailsDelux(context)
                }
                luxrooms==0->{
                    adapter.clear()
                    detailsDelux(context)
                    detailsStandard(context)
                }
                else->{
                    adapter.clear()
                    detailsStandard(context)
                    detailsDelux(context)
                    detailsLuxury(context)
                }

            }




        }

    }
}