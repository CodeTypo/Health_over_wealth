package com.codetypo.healthoverwealth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.register_form.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        auth.signOut()

        registerPassword.addTextChangedListener {

            val str = PasswordStrength.calculateStrength(registerPassword.text.toString())
            tvPasswordStrengthRegister.text = str.getText(this)
            tvPasswordStrengthRegister.setTextColor(str.color)

            pbPasswordStrengthRegister.progressDrawable.setColorFilter(str.color,
                android.graphics.PorterDuff.Mode.SRC_IN)
            when {
                str.getText(this) == "WEAK" -> {
                    pbPasswordStrengthRegister.progress = 25
                }
                str.getText(this) == "MEDIUM" -> {
                    pbPasswordStrengthRegister.progress = 50
                }
                str.getText(this) == "STRONG" -> {
                    pbPasswordStrengthRegister.progress = 75
                }
                else -> {
                    pbPasswordStrengthRegister.progress = 100
                }
            }
        }

        btnSignUp.setOnClickListener {
            Log.d("Action", "clicked");

            if (registerEmail.text.trim().toString().isNotEmpty() || registerPassword.text.trim()
                    .toString()
                    .isNotEmpty()
            ) {
                Log.d("Action", "Input provided");
                createUser(registerEmail.text.trim().toString(),
                    registerPassword.text.trim().toString())
                startActivity(Intent(this, LoginActivity::class.java));

            } else {
                Log.d("Action", "Input required");
            }
        }

    }


    private fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("Task message", "Successful registration")
                    startActivity(Intent(this, FragmentMainActivity::class.java));

                } else {
                    Log.d("Task message", "Failed... " + task.exception)
                }
            }

    }

    override fun onStart() {
        super.onStart()
        auth.signOut();
        val user = auth.currentUser;

        if (user != null) {
            Log.d("Action", "User already logged in");
            startActivity(Intent(this, FragmentMainActivity::class.java));
        }

    }

}