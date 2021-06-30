package com.codetypo.healthoverwealth.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codetypo.healthoverwealth.R

/**
 * This class represents activity for bmi.
 */
class BmiActivity : AppCompatActivity() {

    /**
     * This function is called when BmiActivity is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)
    }
}