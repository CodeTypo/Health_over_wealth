package com.codetypo.healthoverwealth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.codetypo.healthoverwealth.activities.*
import com.codetypo.healthoverwealth.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_fragment_main.*

/**
 * This class represents main FragmentActivity.
 */
class FragmentMainActivity : AppCompatActivity(), WaterFragment.WaterFragmentInterface,
    StepsFragment.StepsFragmentInterface, BottomNavigationView.OnNavigationItemSelectedListener,
    BmiFragment.BmiFragmentInterface, HeartRateFragment.HeartRateFragmentInterface,
    WeightFragment.WeightFragmentInterface {

    private val firebaseRepo: FirebaseRepo = FirebaseRepo()
    private lateinit var navBar: BottomNavigationView
    var stepsFragment: StepsFragment? = null
    var bmiFragment: BmiFragment? = null
    var hrFragment: HeartRateFragment? = null
    var waterFragment: WaterFragment? = null
    var weightFragment: WeightFragment? = null

    /**
     * This function is called when FragmentMainActivity is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_main)

        stepsFragment = StepsFragment()
        bmiFragment = BmiFragment()
        hrFragment = HeartRateFragment()
        waterFragment = WaterFragment()
        weightFragment = WeightFragment()

        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragmentSteps, stepsFragment!!)
        transaction.add(R.id.fragmentBmi, bmiFragment!!)
        transaction.add(R.id.fragmentWeight, weightFragment!!)
        transaction.add(R.id.fragmentWater, waterFragment!!)
        transaction.add(R.id.fragmentHeartrate, hrFragment!!)
        transaction.commit()

        navBar = bottomNavBar
        navBar.setOnNavigationItemSelectedListener(this)

        if (firebaseRepo.getUser() == null) {
            firebaseRepo.createUser().addOnCompleteListener {
                if (it.isSuccessful) {
                    loadTestData()
                } else {
                    Log.d("Main", "Error: ${it.exception!!.message}")
                }
            }
        } else {
            loadTestData()
        }
    }

    private fun loadTestData() {
    }

    /**
     * This function is used to navigate between activities.
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var intent: Intent = when (item.itemId) {
            R.id.navCups -> Intent(this, CupsActivity::class.java)
            R.id.navSettings -> Intent(this, SettingsActivity::class.java)
            else -> {
                return false
            }
        }
        startActivity(intent)
        return true
    }

    /**
     * This function is called when WaterButton is clicked.
     */
    override fun onWaterButtonClicked() {
    }

    /**
     * This function is called when WaterBody is clicked.
     */
    override fun onWaterBodyClicked() {
        val intent = Intent(this, WaterActivity::class.java)
        startActivity(intent)
    }

    /**
     * This function is called when StepsBody is clicked.
     */
    override fun onStepsBodyClicked() {
        val intent = Intent(this, StepsActivity::class.java)
        startActivity(intent)
    }

    /**
     * This function is called when BmiBody is clicked.
     */
    override fun onBmiBodyClicked() {
        val intent = Intent(this, BmiActivity::class.java)
        startActivity(intent)
    }

    /**
     * This function is called when HeartRateBody is clicked.
     */
    override fun onHrBodyClicked() {
        val intent = Intent(this, HeartActivity::class.java)
        startActivity(intent)
    }

    /**
     * This function is called when WeightBody is clicked.
     */
    override fun onWeightBodyClicked() {
    }
}
