package com.codetypo.healthoverwealth.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codetypo.healthoverwealth.R
import kotlinx.android.synthetic.main.fragment_bmi.*


class BmiFragment : Fragment() {

    var bmiInterface: BmiFragmentInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bmi, container, false)
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