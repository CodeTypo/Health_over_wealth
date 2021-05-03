package com.codetypo.healthoverwealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.editEmail
import kotlinx.android.synthetic.main.activity_register.editPassword

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener {
            Log.d("Action", "clicked");

            if (loginEmail.text.trim().toString().isNotEmpty() || loginPassword.text.trim().toString().isNotEmpty())
            {
                Log.d("Action", "Input provided");
                signInUser(loginEmail.text.trim().toString(), loginPassword.text.trim().toString())

            } else {
                Log.d("Action", "Input required");
            }
        }

    }


    fun signInUser(email:String, password:String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                    Log.d("Task message","Successful login")
                    startActivity(Intent(this, MainActivity::class.java));
                }else{
                    Log.d("Task message","Failed... " + task.exception)
                }
            }
    }

}