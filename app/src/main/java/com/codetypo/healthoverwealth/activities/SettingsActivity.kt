package com.codetypo.healthoverwealth.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.codetypo.healthoverwealth.FragmentMainActivity
import com.codetypo.healthoverwealth.R
import com.codetypo.healthoverwealth.models.HeightModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_settings.bottomNavBar

class SettingsActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        navBar = bottomNavBar
        navBar.selectedItemId = R.id.navSettings
        navBar.setOnNavigationItemSelectedListener(this)

        val database = FirebaseDatabase.getInstance()

        val uuid = FirebaseAuth.getInstance().currentUser?.uid

        val heightModel = database.reference.child(uuid.toString()).child("HeightModel")

        btnSaveHeight.setOnClickListener {
            if(etHeight.text.toString().isNotEmpty() && etHeight.text.toString().toDouble() > 1.3 && etHeight.text.toString().toDouble() < 2.3) {
                val heightValue = HeightModel(etHeight.text.toString())
                heightModel.setValue(heightValue)
                tvHeightErrorMessage.text = "Saved!"
                tvHeightErrorMessage.setTextColor(Color.parseColor("#7CC679"))
            }
            else {
                tvHeightErrorMessage.text = "Invalid height value!"
                tvHeightErrorMessage.setTextColor(Color.parseColor("#8B0000"))
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var intent: Intent = when (item.itemId) {
            R.id.navHome -> Intent(this, FragmentMainActivity::class.java)
            R.id.navStats -> Intent(this, StatsActivity::class.java)
            R.id.navCup -> Intent(this, CupActivity::class.java)
            else -> { // Note the block
                return false
            }
        }
        startActivity(intent)
        return true
    }
}