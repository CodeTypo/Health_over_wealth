package com.codetypo.healthoverwealth

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_form.*

/**
 * This class represents activity for login.
 */
class LoginActivity : AppCompatActivity() {

    val ACTIVITY_RECOGNITION_RQ = 101
    val BODY_SENSORS_RQ = 102
    private lateinit var auth: FirebaseAuth

    /**
     * This function is called when LoginActivity is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance();

        checkForPermissions(android.Manifest.permission.ACTIVITY_RECOGNITION,
            "recognition",
            ACTIVITY_RECOGNITION_RQ)
        checkForPermissions(android.Manifest.permission.BODY_SENSORS,
            "body sensor",
            BODY_SENSORS_RQ)


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

    /**
     * This function supports user login based on the entered email address and password.
     */
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        fun innerCheck(name: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "$name permission refused", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        when (requestCode) {
            ACTIVITY_RECOGNITION_RQ -> innerCheck("Activity recognition")
            BODY_SENSORS_RQ -> innerCheck("Body sensors")
        }
    }

    private fun checkForPermissions(permission: String, name: String, requestCode: Int) {
        when {
            ContextCompat.checkSelfPermission(applicationContext,
                permission) == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_SHORT)
                    .show()
            }
            shouldShowRequestPermissionRationale(permission) -> showDialog(permission,
                name,
                requestCode)
            else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        }
    }

    private fun showDialog(permission: String, name: String, requestCode: Int) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setMessage("Permission to access your $name")
            setTitle("Permission required")
            setPositiveButton("OK") { dialog, which ->
                ActivityCompat.requestPermissions(this@LoginActivity,
                    arrayOf(permission),
                    requestCode)
            }
        }
        val dialog = builder.create()
        dialog.show()
    }


}