package com.codetypo.healthoverwealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.codetypo.healthoverwealth.activities.BmiActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnRegister.setOnClickListener {
            Log.d("Action","clicked");

            if(editEmail.text.trim().toString().isNotEmpty() || editPassword.text.trim().toString().isNotEmpty()){
                Log.d("Action","Input provided");
            } else {
                Log.d("Action","Input required");
            }
            startActivity(Intent(this, MainActivity::class.java));        }
    }
}