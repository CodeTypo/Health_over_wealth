package com.codetypo.healthoverwealth

import android.graphics.Color

enum class PasswordStrength(private var resId: Int, color: Int) {

    WEAK(R.string.password_strength_weak, Color.RED),
    MEDIUM(R.string.password_strength_medium, Color.rgb(218, 181, 33)),
    STRONG(R.string.password_strength_strong, Color.GREEN),
    VERY_STRONG(R.string.password_strength_very_strong, Color.rgb(19, 104, 13));

    var color: Int = 0
        internal set

    init {
        this.color = color
    }

    fun getText(ctx: android.content.Context): CharSequence {
        return ctx.getText(resId)
    }

    companion object {

        fun calculateStrength(password: String): PasswordStrength {
            var currentScore = 0


            for (element in password) {

                currentScore += if (element.isLetter()) {
                    if (element.isLowerCase()) {
                        1
                    } else {
                        2
                    }
                } else if (element.isDigit()) {
                    2
                } else {
                    3
                }

            }

            when {
                currentScore < 10 -> return WEAK
                currentScore < 18 -> return MEDIUM
                currentScore < 26 -> return STRONG
            }

            return VERY_STRONG
        }
    }

}