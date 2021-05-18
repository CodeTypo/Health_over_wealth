package com.codetypo.healthoverwealth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_form.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance();

        btnLogIn.setOnClickListener {
            Log.d("Action", "clicked");

            if (loginEmail.text.trim().toString().isNotEmpty() || loginPassword.text.trim()
                    .toString().isNotEmpty()
            ) {
                Log.d("Action", "Input provided");
                signInUser(loginEmail.text.trim().toString(), loginPassword.text.trim().toString())

            } else {
                Log.d("Action", "Input required");
            }
        }

        inCreateAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }


    fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("Task message", "Successful login")
                    startActivity(Intent(this, FragmentMainActivity::class.java));
                } else {
                    Log.d("Task message", "Failed... " + task.exception)
                }
            }
    }

}