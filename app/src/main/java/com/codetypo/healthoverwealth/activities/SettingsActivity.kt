package com.codetypo.healthoverwealth.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.codetypo.healthoverwealth.FragmentMainActivity
import com.codetypo.healthoverwealth.PasswordStrength
import com.codetypo.healthoverwealth.R
import com.codetypo.healthoverwealth.models.HeightModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        navBar = bottomNavBar
        navBar.selectedItemId = R.id.navSettings
        navBar.setOnNavigationItemSelectedListener(this)

        val database = FirebaseDatabase.getInstance()

        val uid = FirebaseAuth.getInstance().currentUser?.uid

        val heightModel = database.reference.child(uid.toString()).child("HeightModel")

        btnSaveHeight.setOnClickListener {
            if (etHeight.text.toString().isNotEmpty() && etHeight.text.toString()
                    .toDouble() > 1.3 && etHeight.text.toString().toDouble() < 2.3
            ) {
                val heightValue = HeightModel(etHeight.text.toString())
                heightModel.setValue(heightValue)
                tvHeightErrorMessage.text = "Saved!"
                tvHeightErrorMessage.setTextColor(Color.parseColor("#7CC679"))
            } else {
                tvHeightErrorMessage.text = "Invalid height value!"
                tvHeightErrorMessage.setTextColor(Color.parseColor("#8B0000"))
            }
        }

        etNewPassword.addTextChangedListener {

            val str = PasswordStrength.calculateStrength(etNewPassword.text.toString())
            tvPasswordStrength.text = str.getText(this)
            tvPasswordStrength.setTextColor(str.color)

            pbPasswordStrength.progressDrawable.setColorFilter(str.color,
                android.graphics.PorterDuff.Mode.SRC_IN)
            if (str.getText(this) == "WEAK") {
                pbPasswordStrength.progress = 25
            } else if (str.getText(this) == "MEDIUM") {
                pbPasswordStrength.progress = 50
            } else if (str.getText(this) == "STRONG") {
                pbPasswordStrength.progress = 75
            } else {
                pbPasswordStrength.progress = 100
            }
        }

        btnSavePassword.setOnClickListener {
            if (etNewPassword.text.toString() == etConfirmPassword.text.toString() && etNewPassword.text.length > 5) {
                val user = FirebaseAuth.getInstance().currentUser
                user?.updatePassword(etNewPassword.text.toString())

                tvPasswordMessage.text = "Password changed!"
                tvPasswordMessage.setTextColor(Color.parseColor("#7CC679"))
            } else if (etNewPassword.text.toString() != etConfirmPassword.text.toString()) {
                tvPasswordMessage.text = "Passwords are different!"
                tvPasswordMessage.setTextColor(Color.parseColor("#8B0000"))
            } else {
                tvPasswordMessage.text = "Password must be at least 6 characters long!"
                tvPasswordMessage.setTextColor(Color.parseColor("#8B0000"))
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var intent: Intent = when (item.itemId) {
            R.id.navHome -> Intent(this, FragmentMainActivity::class.java)
            R.id.navStats -> Intent(this, StatsActivity::class.java)
            R.id.navCup -> Intent(this, CupActivity::class.java)
            else -> {
                return false
            }
        }
        startActivity(intent)
        return true
    }
}