package com.codetypo.healthoverwealth.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import com.codetypo.healthoverwealth.R
import com.codetypo.healthoverwealth.models.WeightModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_weight.*

class WeightFragment : Fragment() {

    var weightInterface: WeightFragmentInterface? = null

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

        val database = FirebaseDatabase.getInstance()

        val uid = FirebaseAuth.getInstance().currentUser?.uid

        val weightModel = database.reference.child(uid.toString()).child("WEIGHT_MODEL")

        weightModel.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val weight = snapshot.child("weight")
                    tvWeight.text = weight.getValue(String::class.java)
                    val progress = weight.getValue(String::class.java)!!.toFloat()

                    simpleSeekBar.progress = (progress * 10).toInt()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

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
                val weightValue = WeightModel("" + progress1)

                weightModel.setValue(weightValue)
            }
        })
    }


    interface WeightFragmentInterface {
        fun onWeightBodyClicked()
    }

}