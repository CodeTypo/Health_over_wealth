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
import kotlinx.android.synthetic.main.fragment_heartrate.*

/**
 * This class represents fragment for HeartRate.
 */
class HeartRateFragment : Fragment() {

    var hrInterface: HeartRateFragmentInterface? = null

    val database = FirebaseDatabase.getInstance()
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val heartRateModel = database.reference.child(uid.toString()).child("HEART_RATE_MODEL")

    /**
     * This function is called to create the HeartRateFragment view.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_heartrate, container, false)
    }

    /**
     * This function is called, when HeartRateFragment's view is already created.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        heartRateModel.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    if (snapshot.exists()) {
                        val heartRateValue = snapshot.child("heart_rate")
                        lastHeartRateTV.text =
                            heartRateValue.getValue(String::class.java).toString()
                    }
                } catch (e: Exception) {
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    /**
     * This function is being called while the fragment is being attached, it checks if the class
     * calling this fragment implemented the required interface
     */

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            hrInterface = context as HeartRateFragmentInterface
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener")
        }
    }

    /**
     * After the activity gets created, an onClickListener redirecting to the new activity is being
     * registered to the whole Bmi tile body.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        heartrateBody.setOnClickListener {
            hrInterface?.onHrBodyClicked()
        }
    }

    /**
     * This is HeartRateFragment interface.
     */
    interface HeartRateFragmentInterface {
        fun onHrBodyClicked()
    }

}