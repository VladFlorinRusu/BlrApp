package com.example.aplicatie2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_ranking.*
import kotlinx.android.synthetic.main.register.*
import java.util.*
import kotlin.collections.HashMap

class Register : AppCompatActivity() {
    var teams = arrayListOf<Team>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        readFromFirebase()
        registerButton1.setOnClickListener {
            var email = emailText.text.toString()
            var password = passwordText.text.toString()
            Log.d("YAAA", email)
            Log.d("YAAA", password)

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        // Log.d("Register", "Not successful" )

                        Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener

                    } else //if it is successful
                    {
                        var user = FirebaseAuth.getInstance().currentUser
                        if (user != null) {
                            user.sendEmailVerification().addOnCompleteListener {
                                Log.d("Register", "Mail trimis")
                            }
                        }
                        Log.d("Register", "User creat cu succes ${it.result?.user?.uid}")
                        Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
                        saveUserToDataBase()

                        val intent = Intent(this, Interfata::class.java)
                        // intent.flags =
                        // Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)//asta va inchide activity'ul acesta cand se face trecerea la urm activity
                        startActivity(intent)
                    }


                }

        }

    }

    private fun readFromFirebase() {

        val reference = FirebaseDatabase.getInstance().getReference("/Teams")
        reference.addListenerForSingleValueEvent(object : ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) // e chemata atunci cand iei toate datele din bd; snapshot are toate datele
            {
                while (snapshot == null) {
                }
                for (ds: DataSnapshot in snapshot.children) {
                    println("sunt in data snapshot")
                    val team = ds.getValue(Team::class.java)

                    if (team != null) {
                        teams.add(team)
                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {


            }

        })

    }

    private fun saveUserToDataBase() {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/Teams")
        var user =
            User(nameText.text.toString(), emailText.text.toString(), teamText.text.toString())
        var ok = 0//daca echipa exista deja

        if (teams != null)
            for (t: Team in teams)
                if (t.name.toLowerCase().equals(teamText.text.toString().toLowerCase())) {
                    t.addNewUser(user)
                    ok = 1
                    var hash = hashMapOf<String, Any?>()
                    hash.put(user.name, user)
                    t.users.forEach() {
                        hash.put(it.key, it.value)
                    }
                    hash.forEach() {
                        println(
                            "HASH MAP  ".plus(it.key.toString()).plus("    ")
                                .plus(it.value.toString())
                        )

                    }
                    ref.child(t.name).setValue(t)


                    break
                }
        if (ok == 0) {
            var team = Team(teamText.text.toString(), "Registered", 0, HashMap<String, User>())
            team.addNewUser(user)
            teams.add(team)
            ref.child(team.name.toString()).setValue(team).addOnSuccessListener {
                Toast.makeText(this, "Echipa adaugata", Toast.LENGTH_SHORT).show()
            }

        }

    }


}




