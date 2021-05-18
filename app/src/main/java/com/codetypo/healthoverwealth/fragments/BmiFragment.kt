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

        btnCalculate.setOnClickListener {

            model.weight.observe(viewLifecycleOwner, Observer<Any> { o ->
                val bmiValue = ((o!!.toString().toDouble() * 100 / 1.7.pow(2.0)).toInt() / 100.0)

                tvBMI.text = bmiValue.toString()

                when {
                    bmiValue < 16 -> {
                        tvResult!!.text = "starvation"
                        tvResult.setTextColor(Color.parseColor("FF0000"))
                    }
                    bmiValue < 17 -> {
                        tvResult!!.text = "emaciation"
                        tvResult.setTextColor(Color.parseColor("#FF8C00"))
                    }
                    bmiValue < 18.5 -> {
                        tvResult!!.text = "underweight"
                        tvResult.setTextColor(Color.parseColor("#FFA500"))
                    }
                    bmiValue < 25 -> {
                        tvResult!!.text = "normal"
                        tvResult.setTextColor(Color.parseColor("#7CC679"))
                    }
                    bmiValue < 30 -> {
                        tvResult!!.text = "overweight"
                        tvResult.setTextColor(Color.parseColor("#FFA500"))
                    }
                    bmiValue < 35 -> {
                        tvResult!!.text = "1st degree obesity"
                        tvResult.setTextColor(Color.parseColor("#FF8C00"))
                    }
                    bmiValue < 40 -> {
                        tvResult!!.text = "2nd degree obesity"
                        tvResult.setTextColor(Color.parseColor("#FF0000"))
                    }
                    else -> {
                        tvResult!!.text = "3rd degree obesity"
                        tvResult.setTextColor(Color.parseColor("#8B0000"))
                    }
                }
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