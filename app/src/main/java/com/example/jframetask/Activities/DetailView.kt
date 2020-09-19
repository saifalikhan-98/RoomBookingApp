package com.example.jframetask.Activities

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.jframetask.Adapter.MainPageAdapter
import com.example.jframetask.R
import com.example.jframetask.repo.DataRepo
import com.example.jframetask.repo.SetData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_detail_view.*

class DetailView : AppCompatActivity() {
    lateinit var type:TextView
    lateinit var Price:TextView
    lateinit var Capacity:TextView
    lateinit var bookbtn:Button
    //lateinit var type:TextView
    var getType:String?=null

    lateinit var recyclesimilar:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_view)
        recyclesimilar=findViewById(R.id.similarrooms)
        bookbtn=findViewById(R.id.book)
        Capacity=findViewById(R.id.Capacityoom)
        recyclesimilar.setHasFixedSize(true)
        type=findViewById(R.id.TypeRoom)
        Price=findViewById(R.id.PriceRoom)
        getData()
        populateRecyclerView()
        bookbtn.setOnClickListener {

            makebooking()
        }
        val currentuser=FirebaseAuth.getInstance().currentUser!!.uid
        val db=FirebaseDatabase.getInstance().getReference("Users/").child(currentuser)
        db.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Name=snapshot.child("Name").value.toString()

                Log.d("Name",Name!!)


            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        val dbr=FirebaseDatabase.getInstance().getReference("Rooms/$getType")
        dbr.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val roomsAvail=snapshot.child("Available").value.toString()
                roomsleft=roomsAvail.toInt()-1
                Log.d("Name",Name!!)


            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private fun makebooking() {
        val deluxrooms=MainActivity.AvailableRoomsDelux!!.toInt()
        val Stanrooms=MainActivity.AvailableRoomsStn!!.toInt()
        val luxrooms=MainActivity.AvailableRoomsLux!!.toInt()
        when {
            getType=="Delux" && deluxrooms!=0->{
                SetData.loadDatePickerSingleBooking(this)
            }
            getType=="Standard" && Stanrooms!=0->{
                SetData.loadDatePickerSingleBooking(this)
            }
            getType=="Luxury" && luxrooms !=0->{
                SetData.loadDatePickerSingleBooking(this)
            }
            else->{
                val alertbox=AlertDialog.Builder(this)
                alertbox.setTitle("OOPS!!")
                alertbox.setMessage("Sorry no rooms available of $getType category")
                alertbox.setCancelable(true)
                alertbox.setPositiveButton("Ok",object:DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                    }

                })
                alertbox.show()

            }

        }

    }

    private fun populateRecyclerView() {
        when{

            getType=="Luxury"->{
                DataRepo.adapter.clear()
                DataRepo.detailsLuxury(this)
                recyclesimilar.adapter=DataRepo.adapter

            }
            getType=="Delux"->{
                DataRepo.adapter.clear()
                DataRepo.detailsDelux(this)
                recyclesimilar.adapter=DataRepo.adapter
            }
            getType=="Standard"->{
                DataRepo.adapter.clear()
                DataRepo.detailsStandard(this)
                recyclesimilar.adapter=DataRepo.adapter
            }



        }

    }

    private fun getData() {
         getType=intent.getStringExtra(MainPageAdapter.TYPE)
        roomType=getType
        val getImage=intent.getStringExtra(MainPageAdapter.IMAGE)
        Image=getImage
        val getPrice=intent.getStringExtra(MainPageAdapter.PRICE)
        val getName=intent.getStringExtra(MainPageAdapter.NAME)
        val getcapacity=intent.getStringExtra(MainPageAdapter.CAPACITY)

        Log.d("details","$getName")



       Glide.with(this@DetailView)
           .load(getImage)
           .into(imageView)

        type.text= getType
        Price.text="Rs $getPrice"
        Capacity.text="Capacity : $getcapacity"


    }
    companion object{
        var roomType:String?=null
        var Name:String?=null
        var roomsleft:Int?=null
        var Image:String?=null
    }
}