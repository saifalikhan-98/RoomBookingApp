package com.example.jforceadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.jforceadmin.Repo.DataRepo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.addremovecapacity.view.*
import kotlinx.android.synthetic.main.setcapacity.view.*
import kotlinx.android.synthetic.main.uploadoptions.view.*

class MainActivity : AppCompatActivity() {
    lateinit var recyler: RecyclerView
    var Type:String?=null
    var Capacity:String?=null
    companion object{
        var AvailableRooms:String?=null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyler=findViewById(R.id.bookingview)
        recyler.setHasFixedSize(true)

        initRecycle()

        AddRoom.setOnClickListener {
            showOptions()
        }

    }

    private fun showOptions() {
        val mBuilder=AlertDialog.Builder(this)
        val mDialog=LayoutInflater.from(this).inflate(R.layout.addremovecapacity,null)
        mBuilder.setView(mDialog)
        mBuilder.setTitle("Options")
        val box=mBuilder.show()
        mDialog.add.setOnClickListener {
            showDialogBox()
        }
        mDialog.changeCapacity.setOnClickListener {
            showdbox()
        }

    }

    private fun showdbox() {
        val mBuilder=AlertDialog.Builder(this)
        val mDialog=LayoutInflater.from(this).inflate(R.layout.uploadoptions,null)
        mBuilder.setView(mDialog)
        mBuilder.setTitle("Change ")
        val box=mBuilder.show()
        mDialog.Standard.setOnClickListener {
            Type="Standard"

            getType(Type!!)
            box.dismiss()
            showcapacityDialogBox()
        }
        mDialog.Delux.setOnClickListener {
            Type="Delux"
            getType(Type!!)
            box.dismiss()
            showcapacityDialogBox()
        }
        mDialog.Luxury.setOnClickListener {
            Type="Luxury"
            getType(Type!!)
            box.dismiss()
            showcapacityDialogBox()
        }
    }

    private fun showcapacityDialogBox() {

        val mBuilder=AlertDialog.Builder(this)
        val mDialog=LayoutInflater.from(this).inflate(R.layout.setcapacity,null)
        mBuilder.setView(mDialog)
        val no=mDialog.entercapacity.findViewById<EditText>(R.id.entercapacity)

        val box=mBuilder.show()

        mDialog.ok.setOnClickListener {
            DataRepo.setDataforCapacity(this,Type!!,mDialog.entercapacity.text.toString())
            box.dismiss()
        }

    }

    private fun showDialogBox() {

        val mBuilder=AlertDialog.Builder(this)
        val mDialog=LayoutInflater.from(this).inflate(R.layout.uploadoptions,null)
        mBuilder.setView(mDialog)
        mBuilder.setTitle("Change ")
        val box=mBuilder.show()
        mDialog.Standard.setOnClickListener {
            Type="Standard"
            getType(Type!!)
            box.dismiss()
            showentercapcitydialog()
        }
        mDialog.Delux.setOnClickListener {
            Type="Delux"
            getType(Type!!)
            box.dismiss()
            showentercapcitydialog()
        }
        mDialog.Luxury.setOnClickListener {
            Type="Luxury"
            getType(Type!!)
            box.dismiss()
            showentercapcitydialog()
        }
    }

    private fun showentercapcitydialog() {

        val mBuilder=AlertDialog.Builder(this)
        val mDialog=LayoutInflater.from(this).inflate(R.layout.setcapacity,null)
        mBuilder.setView(mDialog)
        val no=mDialog.entercapacity.findViewById<EditText>(R.id.entercapacity)
        val guests=no.text.toString()
        val box=mBuilder.show()

        mDialog.ok.setOnClickListener {
            Capacity=guests
            DataRepo.setData(this,Type!!,mDialog.entercapacity.text.toString())
            box.dismiss()
        }


    }

    private fun initRecycle() {
        recyler.apply {
            DataRepo.getDataMainPage(this@MainActivity)
            recyler.adapter=DataRepo.adapter
        }

    }
    fun getType(Type:String){
        val db = FirebaseDatabase.getInstance().getReference("Rooms/$Type")
        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val rooms=snapshot.child("Available").value.toString()
                AvailableRooms=rooms
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }
}