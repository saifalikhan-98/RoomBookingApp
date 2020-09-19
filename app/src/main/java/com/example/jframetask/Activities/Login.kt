package com.example.jframetask.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jframetask.R
import com.example.jframetask.repo.loginSignUp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bLogin.setOnClickListener {

            startLogin()
        }
        bClickRegister.setOnClickListener {
            startActivity(Intent(this,SignUp::class.java))
        }
    }

    private fun startLogin() {
        val email=emaillogin.text.toString()
        val password=passowrdlogin.text.toString()
        loginSignUp.Login(email,password,this)
    }
    override fun onStart() {
        val auth=FirebaseAuth.getInstance()
        super.onStart()
        val user=auth.currentUser
        if(user!=null){
            val intent= Intent(this,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

}