package com.example.jframetask.repo

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.icu.util.Calendar
import android.telecom.Call
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.example.jframetask.Activities.DetailView
import com.example.jframetask.Adapter.MainPageAdapter
import com.example.jframetask.R
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.roomselectiondialog.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import kotlin.random.Random

class SetData {

    companion object{
        var Checkin:String?=null
        var checkout:String?=null
        var Roomselected:String?=null

        fun loadDatePickerSingleBooking(context: Context) {


            val currentUser=FirebaseAuth.getInstance().currentUser!!.uid
            val toast= Toast.makeText(context,"Please select a checkin Date",Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
            val cal= java.util.Calendar.getInstance()

            val year=cal.get(Calendar.YEAR)
            val month=cal.get(Calendar.MONTH)
            val day=cal.get(Calendar.DATE)

            val dateDialog= DatePickerDialog(context,
                    DatePickerDialog.OnDateSetListener { datePicker, mYear, mMonth, mDate ->

                   Checkin="$mYear/$mMonth/$mDate"
                        val checkoutDatePicker=DatePickerDialog(context,DatePickerDialog.OnDateSetListener{datePicker, mYear, mMonth, mDate ->
                            val toast= Toast.makeText(context,"Please select a CheckOut Date",Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.CENTER,0,0)
                            toast.show()
                            checkout="$mYear/$mMonth/${mDate.toInt()}"
                            PickRomm(context)
                        },year,month,day)
                        checkoutDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                        checkoutDatePicker.show()



                },year,month,day)
           dateDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dateDialog.show()



            Log.d("checkin","$Checkin")




            }
        fun PickRomm(context: Context){

            val mBuilder=AlertDialog.Builder(context)
            val mDialog = LayoutInflater.from(context).inflate(R.layout.roomselectiondialog,null)
            mBuilder.setView(mDialog)
            mBuilder.setTitle("Please pick a room")

            val box=mBuilder.show()
            val guestNo=mDialog.guestno.findViewById<EditText>(R.id.guestno)
            val number=guestNo.text.toString()

            mDialog.Room1.setOnClickListener {
                if(number!=null) {
                    Roomselected = "Room 1"
                    makebooking(context, Roomselected, checkout, Checkin,mDialog.guestno.text.toString())
                    box.dismiss()
                }
            }
            mDialog.Room2.setOnClickListener {
                if(number!=null) {
                    Roomselected = "Room 2"
                    makebooking(context, Roomselected, checkout, Checkin,mDialog.guestno.text.toString())
                    box.dismiss()
                }
            }


        }

        private fun makebooking(context: Context, roomselected: String?, checkout: String?, checkin: String?,number:String) {
            val bookingid=Random.nextLong(1000,9999)
           var Name=DetailView.Name
            val bookingMap=HashMap<String,Any>()
            val currentUser=FirebaseAuth.getInstance().currentUser!!.uid
            val db=FirebaseDatabase.getInstance().getReference("Users/$currentUser/MyBookings")
           val roomType=DetailView.roomType

            bookingMap["Booking Id"]=bookingid.toString()
            bookingMap["Image"]=DetailView.Image!!
            bookingMap["User Id"]=currentUser
            bookingMap["Name"]=Name!!
            bookingMap["NoOfGuest"]=number
            bookingMap["RoomType"]=roomType!!
            bookingMap["RoomNo"]=roomselected!!
            bookingMap["CheckIn"]=checkin!!
            bookingMap["CheckOut"]=checkout!!


            db.child(bookingid.toString()).setValue(bookingMap).addOnCompleteListener {

            }
            val roomsleft=DetailView.roomsleft
            val availablemap=HashMap<String,Any>()
            availablemap["Available"]=roomsleft!!.toString()
            FirebaseDatabase.getInstance().getReference("Rooms/$roomType").updateChildren(availablemap)

            val dataref=FirebaseDatabase.getInstance().getReference("Bookings")
            dataref.child(bookingid.toString()).setValue(bookingMap).addOnCompleteListener {
                if(it.isSuccessful){
                    loginSignUp.Toastmsg("Booking Confirmed",context)


                }

            }
            val bookeddates=HashMap<String,Any>()
            bookeddates["CheckIn"]=checkin
            bookeddates["CheckOut"]=checkout
            bookeddates["RoomType"]=roomType
            bookeddates["UserId"]=currentUser
            FirebaseDatabase.getInstance().getReference("Booked Dates").child(currentUser).setValue(bookeddates)









        }


    }

    }
