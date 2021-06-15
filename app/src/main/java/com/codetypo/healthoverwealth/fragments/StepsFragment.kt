package com.codetypo.healthoverwealth.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codetypo.healthoverwealth.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_steps.*
import java.time.LocalDate

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StepsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StepsFragment : Fragment() {
    var stepsInterface: StepsFragmentInterface? = null

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        val database = FirebaseDatabase.getInstance()

        val uid = FirebaseAuth.getInstance().currentUser?.uid

        val stepsModel = database.reference.child(uid.toString()).child("StepsModel")

        stepsModel.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    if (snapshot.exists()) {
                        val stepsValue = snapshot.child(LocalDate.now().dayOfWeek.toString())
                        tvStepsMadeToday.text = stepsValue.getValue(String::class.java).toString()
                    }
                } catch (e: Exception) {
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_steps, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            stepsInterface = context as StepsFragmentInterface
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        stepsBody.setOnClickListener {
            stepsInterface?.onStepsBodyClicked()
        }
    }

    interface StepsFragmentInterface {
        fun onStepsBodyClicked()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StepsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StepsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}