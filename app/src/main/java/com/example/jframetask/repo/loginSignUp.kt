package com.example.jframetask.repo

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import com.example.jframetask.Activities.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class loginSignUp {

    companion object{
        private lateinit var auth:FirebaseAuth

        fun Login(email:String,password:String,context: Context){

            when{
                TextUtils.isEmpty(email)->{ Toastmsg("Email cannot be Empty",context) }
                TextUtils.isEmpty(password)->{ Toastmsg("Password cannot be Empty",context) }
                else->{
                    val progressbar=ProgressDialog(context)
                    progressbar.setTitle("Info")
                    progressbar.setMessage("Please Wait this may take a while")
                    progressbar.setCancelable(false)
                    progressbar.show()
                    auth=FirebaseAuth.getInstance()
                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                        if(it.isSuccessful){
                            progressbar.dismiss()
                            val intent= Intent(context,MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)
                        }
                        else{
                            progressbar.dismiss()
                            val message=it.exception!!.localizedMessage
                            Toastmsg(message,context)
                            auth.signOut()
                        }
                    }
                }



            }


        }
        fun SignUp(context: Context,email:String,username:String,mobile:String,password:String,confrimPassword:String){

            when{

                TextUtils.isEmpty(email)->{
                    Toastmsg("Email is required",context)
                }
                TextUtils.isEmpty(username)->{
                    Toastmsg("Name is required",context)
                }
                TextUtils.isEmpty(mobile)->{
                    Toastmsg("Phone No is required",context)
                }
                TextUtils.isEmpty(password)->{
                    Toastmsg("Password is required",context)
                }
                TextUtils.isEmpty(confrimPassword)->{
                    Toastmsg("Password is required",context)
                }
                password==confrimPassword->{

                    val progressbar= ProgressDialog(context)
                    progressbar.setTitle("Info")
                    progressbar.setMessage("This may take a while")
                    progressbar.setCancelable(false)
                    progressbar.show()
                    val mauth=FirebaseAuth.getInstance()
                    mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                        if(it.isSuccessful){
                            
                            createUserDb(email,username,mobile,progressbar,context)
                        }
                        else{
                            progressbar.dismiss()
                            val message=it.exception!!.localizedMessage
                            Toastmsg(message,context)
                            mauth.signOut()
                        }

                    }

                }
                else->{

                    Toastmsg("Password Mismatch",context)

                }



            }



        }

        private fun createUserDb(email: String, username: String, mobile: String, progressbar: ProgressDialog,context: Context) {


            val currentUser=FirebaseAuth.getInstance().currentUser!!.uid
            val db= FirebaseDatabase.getInstance().getReference("Users/")
            val userMap=HashMap<String,Any>()

            userMap["uid"]=currentUser
            userMap["Name"]=username
            userMap["Email"]=email
            userMap["Mobile"]=mobile
            db.child(currentUser).setValue(userMap).addOnCompleteListener { task ->

                if(task.isSuccessful){

                    progressbar.dismiss()
                    Toastmsg("Account Created",context)
                    val intent= Intent(context,MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)


                }
                else{
                    progressbar.dismiss()
                    val message=task.exception?.localizedMessage
                    Toastmsg(message?:"Unexpected Error Please try Again",context)
                    auth.signOut()
                }

            }

        }

        fun Toastmsg(text:String,context: Context){
            val toast= Toast.makeText(context,text,Toast.LENGTH_SHORT)
            toast.show()

        }
    }

}