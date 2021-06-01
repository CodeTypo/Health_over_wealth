package com.codetypo.healthoverwealth.models

class BmiModel {
    var bmi: String? = ""
    var result: String? = ""
    var color: String? = ""

    constructor(bmi: String?, result: String?, color: String?) {
        this.bmi = bmi
        this.result = result
        this.color = color
    }
}