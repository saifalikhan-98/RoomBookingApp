package com.example.jframetask.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jframetask.R
import com.example.jframetask.repo.loginSignUp
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        bSignUp.setOnClickListener {
            startRegistration()
        }
        BackLogin.setOnClickListener {
            startActivity(Intent(this,Login::class.java))
            finish()
        }

    }

    private fun startRegistration() {

        val email=emailsignup.text.toString()
        val userName=usernamesignup.text.toString()
        val password=passowrdsignup.text.toString()
        val phone=phoneno.text.toString()
        val confirmpassword=confirmpassowrd.text.toString()
        loginSignUp.SignUp(this,email,userName,phone,password,confirmpassword)

    }
}