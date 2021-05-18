package com.codetypo.healthoverwealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.codetypo.healthoverwealth.activities.BmiActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.login_form.*
import kotlinx.android.synthetic.main.register_form.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance();
        auth.signOut();

        btnSignUp.setOnClickListener {
            Log.d("Action", "clicked");

            if (registerEmail.text.trim().toString().isNotEmpty() || registerPassword.text.trim().toString()
                    .isNotEmpty()
            ) {
                Log.d("Action", "Input provided");
                createUser(registerEmail.text.trim().toString(), registerPassword.text.trim().toString())
                startActivity(Intent(this, LoginActivity::class.java));

            } else {
                Log.d("Action", "Input required");
            }
        }

//        btnSignUp.setOnClickListener {
//            startActivity(Intent(this, LoginActivity::class.java));
//        }

    }


    fun createUser(email:String, password:String){
       auth.createUserWithEmailAndPassword(email,password)
           .addOnCompleteListener(this){ task ->
               if(task.isSuccessful){
                   Log.d("Task message","Successful registration")
                   startActivity(Intent(this, FragmentMainActivity::class.java));

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
            startActivity(Intent(this, FragmentMainActivity::class.java));
        }

    }

}