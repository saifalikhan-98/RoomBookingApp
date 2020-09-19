package com.example.jforceadmin.Repo

import android.content.Context
import android.widget.Toast
import com.example.jforceadmin.MainActivity
import com.example.jforceadmin.adapters.MainPageAdapter
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

        fun getDataMainPage(context: Context) {

            val db = FirebaseDatabase.getInstance().getReference("Bookings/")
            db.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val getData = it.getValue(rooms::class.java)!!
                        adapter.add(MainPageAdapter(context, getData))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        }

        fun setDataforCapacity(context:Context,Type:String,Capacity:String){
            var AvailableRooms:Int?=null
            val db = FirebaseDatabase.getInstance().getReference("Rooms/$Type")
            db.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val rooms=snapshot.child("Available").value.toString()
                    AvailableRooms=rooms.toInt() + 1
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })


            val setRoom=HashMap<String,Any>()


            setRoom["Capacity"]=Capacity

            FirebaseDatabase.getInstance().getReference("Rooms/$Type").updateChildren(setRoom).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(context,"Changes Saved",Toast.LENGTH_SHORT).show()
                }
            }

        }

        fun setData(context:Context,Type:String,Capacity:String){


            val getRooms=MainActivity.AvailableRooms!!.toInt()+1
            val setRoom=HashMap<String,Any>()


            setRoom["Capacity"]=Capacity
            setRoom["Available"]=getRooms.toString()

            FirebaseDatabase.getInstance().getReference("Rooms/$Type").updateChildren(setRoom).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(context,"Changes Saved",Toast.LENGTH_SHORT).show()
                }
            }


        }
    }
}