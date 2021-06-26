package com.codetypo.healthoverwealth.activities

import DailyWaterView
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codetypo.healthoverwealth.FragmentMainActivity
import com.codetypo.healthoverwealth.R
import com.codetypo.healthoverwealth.WaterAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_stats.*
import java.time.LocalDate

class StatsActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navBar: BottomNavigationView
    val data = ArrayList<DailyWaterView>()
    val today = LocalDate.now().dayOfWeek
    val database = FirebaseDatabase.getInstance()
    val uid = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        navBar = bottomNavBar
        navBar.selectedItemId = R.id.navStats

        navBar.setOnNavigationItemSelectedListener(this)

        val recyclerView = findViewById<RecyclerView>(R.id.historyRecView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        for (i in 0..6) {
            val waterDrunkModel =
                database.reference.child(uid.toString()).child("WATER_DRUNK_MODEL")
            waterDrunkModel.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val weekDay =
                        snapshot.child(LocalDate.now().dayOfWeek.minus(i.toLong()).toString()
                            .toLowerCase())
                    if (weekDay.exists()) {
                        val cup =
                            weekDay.child("drunk_cups").getValue(String::class.java).toString()
                        val cupsTarget =
                            weekDay.child("cups_target").getValue(String::class.java).toString()
                        val drunkWater =
                            weekDay.child("drunk_water").getValue(String::class.java).toString()
                        val waterTarget =
                            weekDay.child("water_target").getValue(String::class.java).toString()

                        if (cupsTarget != "null")
                            data.add(DailyWaterView(today.minus(i.toLong()).toString()
                                .toLowerCase(),
                                cup,
                                drunkWater,
                                cupsTarget,
                                waterTarget))
                        else
                            data.add(DailyWaterView(today.minus(i.toLong()).toString()
                                .toLowerCase(), cup, drunkWater, "8", "2000"))
                    }
                    val adapter = WaterAdapter(data)
                    recyclerView.adapter = adapter
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var intent: Intent = when (item.itemId) {
            R.id.navHome -> Intent(this, FragmentMainActivity::class.java)
//            R.id.navCup -> Intent(this, CupActivity::class.java)
            R.id.navSettings -> Intent(this, SettingsActivity::class.java)
            else -> { // Note the block
                return false
            }
        }
        startActivity(intent)
        return true
    }
}