package com.codetypo.healthoverwealth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.register_form.*

/**
 * This class represents activity for register.
 */
class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    /**
     * This function is called when RegisterActivity is created.
     */
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

    /**
     * This function creates the user.
     */
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

    /**
     * This function redirects the user to the main activity of the application.
     */
    override fun onStart() {
        super.onStart()
        auth.signOut();
        val user = auth.currentUser;

        if (user != null) {
            Log.d("Action", "User already logged in")
            startActivity(Intent(this, FragmentMainActivity::class.java))
        }

    }

}