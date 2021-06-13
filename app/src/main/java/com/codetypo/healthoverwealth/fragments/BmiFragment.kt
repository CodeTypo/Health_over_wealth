package com.codetypo.healthoverwealth.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.codetypo.healthoverwealth.R
import com.codetypo.healthoverwealth.communicator.Communicator
import com.codetypo.healthoverwealth.models.BmiModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_bmi.*
import kotlin.math.pow


class BmiFragment : Fragment() {

    var bmiInterface: BmiFragmentInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bmi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = ViewModelProviders.of(requireActivity()).get(Communicator::class.java)
        val database = FirebaseDatabase.getInstance()

        val uid = FirebaseAuth.getInstance().currentUser?.uid

        val bmiModel = database.reference.child(uid.toString()).child("BmiModel")
        val heightModel = database.reference.child(uid.toString()).child("HeightModel")
        var height = 1.8

        bmiModel.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    if (snapshot.exists()) {
                        val bmi = snapshot.child("bmi")
                        val result = snapshot.child("result")
                        val color = snapshot.child("color")
                        tvBMI.text = bmi.getValue(String::class.java)
                        tvResult.text = result.getValue(String::class.java)
                        tvResult.setTextColor(Color.parseColor(color.getValue(String::class.java)))
                    }
                } catch (e: Exception) {

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        heightModel.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    if (snapshot.exists()) {
                        val heightValue = snapshot.child("height")
                        height = heightValue.getValue(String::class.java).toString().toDouble()
                    }
                } catch (e: Exception) {
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        btnCalculate.setOnClickListener {

            model.weight.observe(viewLifecycleOwner, Observer<Any> { o ->
                val bmiValue = ((o!!.toString().toDouble() * 100 / height.pow(2.0)).toInt() / 100.0)

                tvBMI.text = bmiValue.toString()

                val colorString: String
                val result: String

                when {
                    bmiValue < 16 -> {
                        tvResult!!.text = "starvation"
                        tvResult.setTextColor(Color.parseColor("#FF0000"))
                        colorString = "#FF0000"
                        result = "starvation"
                    }
                    bmiValue < 17 -> {
                        tvResult!!.text = "emaciation"
                        tvResult.setTextColor(Color.parseColor("#FF8C00"))
                        colorString = "#FF8C00"
                        result = "emaciation"
                    }
                    bmiValue < 18.5 -> {
                        tvResult!!.text = "underweight"
                        tvResult.setTextColor(Color.parseColor("#FFA500"))
                        colorString = "#FFA500"
                        result = "underweight"
                    }
                    bmiValue < 25 -> {
                        tvResult!!.text = "normal"
                        tvResult.setTextColor(Color.parseColor("#7CC679"))
                        colorString = "#7CC679"
                        result = "normal"
                    }
                    bmiValue < 30 -> {
                        tvResult!!.text = "overweight"
                        tvResult.setTextColor(Color.parseColor("#FFA500"))
                        colorString = "#FFA500"
                        result = "overweight"
                    }
                    bmiValue < 35 -> {
                        tvResult!!.text = "1st degree obesity"
                        tvResult.setTextColor(Color.parseColor("#FF8C00"))
                        colorString = "#FF8C00"
                        result = "1st degree obesity"
                    }
                    bmiValue < 40 -> {
                        tvResult!!.text = "2nd degree obesity"
                        tvResult.setTextColor(Color.parseColor("#FF0000"))
                        colorString = "#FF0000"
                        result = "2nd degree obesity"
                    }
                    else -> {
                        tvResult!!.text = "3rd degree obesity"
                        tvResult.setTextColor(Color.parseColor("#8B0000"))
                        colorString = "#8B0000"
                        result = "3rd degree obesity"
                    }
                }

                val newBmiValue = BmiModel(bmiValue.toString(), result, colorString)

                bmiModel.setValue(newBmiValue)
            })
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            bmiInterface = context as BmiFragmentInterface
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bmiBody.setOnClickListener {
            bmiInterface?.onBmiBodyClicked()
        }
    }

    interface BmiFragmentInterface {
        fun onBmiBodyClicked()
    }
}