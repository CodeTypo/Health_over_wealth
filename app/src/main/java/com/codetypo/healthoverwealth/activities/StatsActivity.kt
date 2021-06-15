package com.codetypo.healthoverwealth.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.codetypo.healthoverwealth.FragmentMainActivity
import com.codetypo.healthoverwealth.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_stats.*

class StatsActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navBar: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        navBar = bottomNavBar
        navBar.selectedItemId = R.id.navStats

        navBar.setOnNavigationItemSelectedListener(this)

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