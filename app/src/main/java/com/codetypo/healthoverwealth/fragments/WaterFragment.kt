package com.codetypo.healthoverwealth.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codetypo.healthoverwealth.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_water.*
import java.time.LocalDate

class WaterFragment : Fragment() {

    var waterInterface: WaterFragmentInterface? = null
    val database = FirebaseDatabase.getInstance()
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val waterDrunkModel = database.reference.child(uid.toString()).child("WaterDrunkModel")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_water, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            waterInterface = context as WaterFragmentInterface
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        waterDrunkModel.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    if (snapshot.exists()) {
                        val waterDrunkValue = snapshot.child(LocalDate.now().dayOfWeek.toString())
                        milliliters.text =
                            waterDrunkValue.getValue(String::class.java).toString()
                        cupCounter.text = (milliliters.text.toString().toInt() / 250).toString()
                    }
                } catch (e: Exception) {
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        increaseButton.setOnClickListener {
            val cups = cupCounter.text.toString().toInt() + 1
            cupCounter.text = (cups).toString()
            milliliters.text = (cups * 250).toString()

            waterDrunkModel.child("" + LocalDate.now().dayOfWeek)
                .setValue(milliliters.text.toString())
        }

        decreaseButton.setOnClickListener {
            var cups = cupCounter.text.toString().toInt() - 1
            if (cups < 0)
                cups = 0
            cupCounter.text = (cups).toString()
            milliliters.text = (cups * 250).toString()

            waterDrunkModel.child("" + LocalDate.now().dayOfWeek)
                .setValue(milliliters.text.toString())
        }

        waterBody.setOnClickListener {
            waterInterface?.onWaterBodyClicked();
        }

    }

    // Container Activity must implement this interface
    interface WaterFragmentInterface {
        fun onWaterButtonClicked()
        fun onWaterBodyClicked()
    }

}