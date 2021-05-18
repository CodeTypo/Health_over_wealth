package com.codetypo.healthoverwealth.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.codetypo.healthoverwealth.R
import com.codetypo.healthoverwealth.communicator.Communicator
import kotlinx.android.synthetic.main.fragment_weight.*

class WeightFragment : Fragment() {

    var weightInterface: WeightFragmentInterface? = null

    companion object {
        lateinit var mctx: Context
    }

    private var model: Communicator? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weight, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            weightInterface = context as WeightFragmentInterface
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        weightBody.setOnClickListener {
            weightInterface?.onWeightBodyClicked()
        }

        simpleSeekBar?.setOnSeekBarChangeListener(object :
            OnSeekBarChangeListener {

            var progress1 = 0f

            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                tvWeight!!.text = (progress / 10.0).toString()

                progress1 = ((progress / 10.0).toFloat())
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                model = ViewModelProviders.of(activity!!).get(Communicator::class.java)

                model!!.setWeight("" + progress1)

                val myfragment = BmiFragment()
                val fragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentBmi, myfragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        })
    }


    interface WeightFragmentInterface {
        fun onWeightBodyClicked()
    }

}