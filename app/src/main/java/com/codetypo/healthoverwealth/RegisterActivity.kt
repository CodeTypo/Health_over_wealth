package com.codetypo.healthoverwealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.codetypo.healthoverwealth.activities.BmiActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance();
        auth.signOut();

        btnRegister.setOnClickListener {
            Log.d("Action", "clicked");

            if (editEmail.text.trim().toString().isNotEmpty() || editPassword.text.trim().toString()
                    .isNotEmpty()
            ) {
                Log.d("Action", "Input provided");
                createUser(editEmail.text.trim().toString(), editPassword.text.trim().toString())

            } else {
                Log.d("Action", "Input required");
            }
        }

        btnLoginActivity.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java));
        }

    }


    fun createUser(email:String, password:String){
       auth.createUserWithEmailAndPassword(email,password)
           .addOnCompleteListener(this){ task ->
               if(task.isSuccessful){
                   Log.d("Task message","Successful registration")
                   startActivity(Intent(this, MainActivity::class.java));

               }else{
                   Log.d("Task message","Failed... " + task.exception)
               }
           }

    }

    override fun onStart() {
        super.onStart()
        auth.signOut();
        val user = auth.currentUser;

        if(user!= null){
            Log.d("Action","User already logged in");
            startActivity(Intent(this, MainActivity::class.java));
        }

    }

}