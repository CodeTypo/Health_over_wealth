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

class HeartRateFragment : Fragment() {

    var hrInterface: HeartrateFragmentInterface? = null

    val database = FirebaseDatabase.getInstance()
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val heartRateModel = database.reference.child(uid.toString()).child("HEART_RATE_MODEL")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_heartrate, container, false)
    }

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

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            hrInterface = context as HeartrateFragmentInterface
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        heartrateBody.setOnClickListener {
            hrInterface?.onHrBodyClicked()
        }
    }

    interface HeartrateFragmentInterface {
        fun onHrBodyClicked()
    }

}