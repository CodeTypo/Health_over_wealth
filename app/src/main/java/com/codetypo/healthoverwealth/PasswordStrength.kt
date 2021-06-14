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

        private var REQUIRED_LENGTH = 6
        private var REQUIRE_SPECIAL_CHARACTERS = true
        private var REQUIRE_DIGITS = true
        private var REQUIRE_LOWER_CASE = true
        private var REQUIRE_UPPER_CASE = false

        fun calculateStrength(password: String): PasswordStrength {
            var currentScore = 0
            var sawUpper = false
            var sawLower = false
            var sawDigit = false
            var sawSpecial = false


            for (element in password) {
                val c = element

                if (!sawSpecial && !Character.isLetterOrDigit(c)) {
                    currentScore += 1
                    sawSpecial = true
                } else {
                    if (!sawDigit && Character.isDigit(c)) {
                        currentScore += 1
                        sawDigit = true
                    } else {
                        if (!sawUpper || !sawLower) {
                            if (Character.isUpperCase(c))
                                sawUpper = true
                            else
                                sawLower = true
                            if (sawUpper && sawLower)
                                currentScore += 1
                        }
                    }
                }

            }

            if (password.length > REQUIRED_LENGTH) {
                if (REQUIRE_SPECIAL_CHARACTERS && !sawSpecial
                    || REQUIRE_UPPER_CASE && !sawUpper
                    || REQUIRE_LOWER_CASE && !sawLower
                    || REQUIRE_DIGITS && !sawDigit
                ) {
                    currentScore = 1
                } else {
                    currentScore = 2
                    if (password.length > 15) {
                        currentScore = 3
                    }
                }
            } else {
                currentScore = 0
            }

            when (currentScore) {
                0 -> return WEAK
                1 -> return MEDIUM
                2 -> return STRONG
                3 -> return VERY_STRONG
            }

            return VERY_STRONG
        }
    }

}