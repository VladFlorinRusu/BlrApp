package com.example.aplicatie2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    internal var TAG="EMAILUI_AUTH"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        registerButton1.setOnClickListener{
            Log.d("Main shiet"," am apasat register")
             val intent= Intent(this, Register::class.java)
            startActivity(intent)
        }
        loginButton.setOnClickListener{
            Log.d("Main shiet"," am apasat login")
            val intent= Intent(this, Login::class.java)
            startActivity(intent)

        }
    }





    }



