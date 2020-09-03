package com.example.aplicatie2

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.register.*

class Login :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        loginButton.setOnClickListener(){

            var email=emailText.text.toString()
            var password=passwordText.text.toString()

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(){

                if(!it.isSuccessful)
                {   Log.d("Login", "Login failed")
                    return@addOnCompleteListener

                    Toast.makeText(this,"Login Failed", Toast.LENGTH_SHORT).show()
                }
                else
                    Log.d("Login", "Login was a succes " )
                val intent = Intent(this, Login::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(Intent(this, Interfata::class.java))

                Toast.makeText(this,"Login Success", Toast.LENGTH_SHORT).show()
            }
        }

    }

}