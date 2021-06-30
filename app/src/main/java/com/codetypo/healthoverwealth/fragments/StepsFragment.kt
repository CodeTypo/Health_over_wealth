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
import kotlinx.android.synthetic.main.fragment_steps.*
import java.time.LocalDate

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * This class represents fragment for steps.
 */
class StepsFragment : Fragment() {
    var stepsInterface: StepsFragmentInterface? = null

    private var param1: String? = null
    private var param2: String? = null
    val database = FirebaseDatabase.getInstance()
    val uid = FirebaseAuth.getInstance().currentUser?.uid

    /**
     * This function is called when StepsFragment is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        val stepsModel = database.reference.child(uid.toString()).child("STEPS_MODEL")

        stepsModel.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    if (snapshot.exists()) {
                        val stepsValue =
                            snapshot.child(LocalDate.now().dayOfWeek.toString().toLowerCase())
                        if (!stepsValue.equals("null"))
                            tvStepsMadeToday.text =
                                stepsValue.getValue(String::class.java).toString()
                        else
                            tvStepsMadeToday.text = "0"
                    }
                } catch (e: Exception) {
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    /**
     * This function is called to create the StepsFragment view.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_steps, container, false)
    }

    /**
     * This function is being called while the fragment is being attached, it checks if the class
     * calling this fragment implemented the required interface
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            stepsInterface = context as StepsFragmentInterface
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
        stepsBody.setOnClickListener {
            stepsInterface?.onStepsBodyClicked()
        }
    }

    /**
     * This is StepsFragment interface.
     */
    interface StepsFragmentInterface {
        fun onStepsBodyClicked()
    }
}