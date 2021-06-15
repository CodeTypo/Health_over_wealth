package com.codetypo.healthoverwealth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.codetypo.healthoverwealth.activities.*
import com.codetypo.healthoverwealth.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_fragment_main.*


class FragmentMainActivity : AppCompatActivity(), WaterFragment.WaterFragmentInterface,
    StepsFragment.StepsFragmentInterface, BottomNavigationView.OnNavigationItemSelectedListener,
    BmiFragment.BmiFragmentInterface, HeartRateFragment.HeartrateFragmentInterface,
    WeightFragment.WeightFragmentInterface {

    private val firebaseRepo: FirebaseRepo = FirebaseRepo()
    private lateinit var navBar: BottomNavigationView
    var stepsFragment: StepsFragment? = null
    var bmiFragment: BmiFragment? = null
    var hrFragment: HeartRateFragment? = null
    var waterFragment: WaterFragment? = null
    var weightFragment: WeightFragment? = null


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

        //check user login
        if (firebaseRepo.getUser() == null) {
            //create a new one
            firebaseRepo.createUser().addOnCompleteListener {
                if (it.isSuccessful) {
                    //load data
                    loadTestData()
                } else {
                    Log.d("Main", "Error: ${it.exception!!.message}")
                }
            }
        } else {
            //user logged in
            loadTestData()
        }
    }

    private fun loadTestData() {
        firebaseRepo.getPostlist().addOnCompleteListener {
            if (it.isSuccessful) {
                //postList = it.result!!.toObjects(PostModel::class.java)
                // postListAdapter.postListItems = postList
            } else {
                Log.d("Main", "Error: ${it.exception!!.message}")
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var intent: Intent = when (item.itemId) {
            R.id.navStats -> Intent(this, StatsActivity::class.java)
//            R.id.navCup -> Intent(this, CupActivity::class.java)
            R.id.navSettings -> Intent(this, SettingsActivity::class.java)
            else -> { // Note the block
                return false
            }
        }
        startActivity(intent)
        return true
    }

    override fun onWaterButtonClicked() {
        Toast.makeText(this, "DZIAłA", Toast.LENGTH_SHORT).show()
    }

    override fun onWaterBodyClicked() {
        val intent = Intent(this, WaterActivity::class.java)
        startActivity(intent)
    }

    override fun onStepsBodyClicked() {
        val intent = Intent(this, StepsActivity::class.java)
        startActivity(intent)
    }

    override fun onBmiBodyClicked() {
        val intent = Intent(this, BmiActivity::class.java)
        startActivity(intent)
    }

    override fun onHrBodyClicked() {
        val intent = Intent(this, HeartActivity::class.java)
        startActivity(intent)
    }

    override fun onWeightBodyClicked() {
    }
}
