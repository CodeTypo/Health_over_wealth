package com.codetypo.healthoverwealth.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.codetypo.healthoverwealth.R
import kotlinx.android.synthetic.main.fragment_water.*

class WaterFragment : Fragment() {

    var waterInterface: WaterFragmentInterface? = null

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        increaseButton.setOnClickListener{
//            waterInterface?.onWaterButtonClicked();
            val cups = cupCounter.text.toString().toInt()+1
            cupCounter.text = (cups).toString()
            milliliters.text = (cups * 250).toString()
        }

        decreaseButton.setOnClickListener{
//            waterInterface?.onWaterButtonClicked();
            var cups = cupCounter.text.toString().toInt()-1
            if(cups < 0)
                cups = 0
            cupCounter.text = (cups).toString()
            milliliters.text = (cups * 250).toString()
        }

        waterBody.setOnClickListener{
            waterInterface?.onWaterBodyClicked();
        }

    }

    // Container Activity must implement this interface
    interface WaterFragmentInterface {
        fun onWaterButtonClicked()
        fun onWaterBodyClicked()
    }

}