package com.example.jframetask.Activities

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.jframetask.R
import com.example.jframetask.repo.DataRepo
import com.example.jframetask.repo.SetData
import com.example.jframetask.repo.loginSignUp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.filterlayout.view.*

class MainActivity : AppCompatActivity() {
    lateinit var recyler:RecyclerView

    lateinit var recylestandard:RecyclerView
    lateinit var recyledelux:RecyclerView
    lateinit var recyleluxury:RecyclerView
    lateinit var byDate:Button
    lateinit var byRoom:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyler=findViewById(R.id.Rooms)
        recylestandard=findViewById(R.id.standardRooms)
        recyledelux=findViewById(R.id.DeluxRooms)
        recyleluxury=findViewById(R.id.LuxuryRooms)
        byDate=findViewById(R.id.datefilter)
        byRoom=findViewById(R.id.byRoomtype)
        recyler.setHasFixedSize(true)
        DataRepo.adapter.clear()
        initRecycle()
        getCheckinoutdates()
        byRoom.setOnClickListener {
            fiterResult()
        }
        byDate.setOnClickListener {
           loadDatePickerSingleBooking()

        }




    }

    private fun getCheckinoutdates() {

        val dbr= FirebaseDatabase.getInstance().getReference("Rooms/Delux")
        dbr.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val roomsAvail=snapshot.child("Available").value.toString()
                AvailableRoomsDelux=roomsAvail



            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        val dbr1= FirebaseDatabase.getInstance().getReference("Rooms/Standard")
        dbr1.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val roomsAvail=snapshot.child("Available").value.toString()
                AvailableRoomsStn=roomsAvail



            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        val dbr2= FirebaseDatabase.getInstance().getReference("Rooms/Luxury")
        dbr2.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val roomsAvail=snapshot.child("Available").value.toString()
                AvailableRoomsLux=roomsAvail



            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private fun loadDatePickerSingleBooking() {
        val cal= java.util.Calendar.getInstance()

        val year=cal.get(Calendar.YEAR)
        val month=cal.get(Calendar.MONTH)
        val day=cal.get(Calendar.DATE)

        val dateDialog= DatePickerDialog(this@MainActivity,
            DatePickerDialog.OnDateSetListener { datePicker, mYear, mMonth, mDate ->

                checkin ="$mYear/$mMonth/$mDate"
                val checkoutDatePicker= DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener{ datePicker, mYear, mMonth, mDate ->
                        checkout ="$mYear/$mMonth/${mDate.toInt()}"
                        showFilteredResult()
                        loginSignUp.Toastmsg("$checkin,$checkout",this@MainActivity)

                },year,month,day)
                checkoutDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                checkoutDatePicker.show()



            },year,month,day)
        dateDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dateDialog.show()


    }

    private fun showFilteredResult(){

        DataRepo.datefilterData(this@MainActivity)

    }

    private fun fiterResult() {

        val mBuilder=AlertDialog.Builder(this)
        val mDialog=LayoutInflater.from(this).inflate(R.layout.filterlayout,null)
        mBuilder.setView(mDialog)
        mBuilder.setTitle("Please Select")
        val box=mBuilder.show()
        mDialog.Standrdbtn.setOnClickListener {
            showStandardonly()
            box.dismiss()
        }
        mDialog.Deluxbtn.setOnClickListener {
            showDeluxonly()
            box.dismiss()
        }
        mDialog.Luxurybtn.setOnClickListener {
            showLuxuryOnly()
            box.dismiss()
        }
        mDialog.allbtn.setOnClickListener {
            initRecycle()
            box.dismiss()
        }


    }


    private fun showStandardonly(){
        DataRepo.adapter.clear()
        recyler.visibility= GONE
        recyleluxury.visibility= GONE
        recyledelux.visibility= GONE
        recylestandard.visibility=View.VISIBLE
        DataRepo.standardonly(this@MainActivity)
        recylestandard.adapter=DataRepo.adapter
        recylestandard.adapter

    }

    private fun showDeluxonly(){
        DataRepo.adapter.clear()
        recyler.visibility= GONE
        recyleluxury.visibility= GONE
        recylestandard.visibility= GONE
        recyledelux.visibility=View.VISIBLE
        DataRepo.deluxonly(this@MainActivity)
        recyledelux.adapter=DataRepo.adapter

    }

    private fun showLuxuryOnly(){
        DataRepo.adapter.clear()
        recyler.visibility= GONE
        recylestandard.visibility= GONE
        recyledelux.visibility= GONE
        recyleluxury.visibility=View.VISIBLE
        DataRepo.luxuryonly(this@MainActivity)
        recyleluxury.adapter=DataRepo.adapter

    }

    private fun initRecycle() {
        recyler.visibility=View.VISIBLE
        recyleluxury.visibility= GONE
        recyledelux.visibility= GONE
        recylestandard.visibility= GONE
        DataRepo.adapter.clear()
        DataRepo.getDataMainPage(this@MainActivity)
        recyler.adapter=DataRepo.adapter
        DataRepo.adapter.clear()

                /* val img1=snapshot.child("Room1/").child("/Images").child("/url").getValue().toString()
                 imageList.add(SlideModel(img1,"Delux",
                     ScaleTypes.FIT))*/



    }
    companion object{
        var checkin:String?=null
        var checkout:String?=null
        val dbin:String?=null
        val dbout:String?=null
        var AvailableRoomsDelux:String?=null
        var AvailableRoomsStn:String?=null
        var AvailableRoomsLux:String?=null
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()

    }


}