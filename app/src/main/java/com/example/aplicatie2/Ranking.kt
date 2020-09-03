package com.example.aplicatie2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_ranking.*
import kotlinx.android.synthetic.main.team_rankings.view.*

class Ranking : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)
        supportActionBar?.title = "Rankings"

        fetchUsers()
    }

    private fun fetchUsers() {

        val reference = FirebaseDatabase.getInstance().getReference("/Teams")
        reference.addListenerForSingleValueEvent(object : ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) // e chemata atunci cand iei toate datele din bd; snapshot are toate datele
            {
                val adapter = GroupAdapter<ViewHolder>()
                snapshot.children.forEach {
                    val team = it.getValue(Team::class.java)
                    if (team != null)
                        adapter.add(TeamRanking(team))
                }
                recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

    }
}

class TeamRanking(val team: Team) : Item<ViewHolder>() {


    override fun getLayout(): Int {
        return R.layout.team_rankings
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textViewTeam.text = team.name
        viewHolder.itemView.textViewStatus.text = team.status

    }
}