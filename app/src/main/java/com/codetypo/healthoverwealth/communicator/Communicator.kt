package com.codetypo.healthoverwealth.communicator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Communicator : ViewModel() {
    val weight = MutableLiveData<Any>()

    fun setWeight(msg: String) {
        weight.value = msg
    }
}