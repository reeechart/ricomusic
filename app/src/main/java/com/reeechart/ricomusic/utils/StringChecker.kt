package com.reeechart.ricomusic.utils

/**
 * Created by Reeechart on 06-May-19.
 */
class StringChecker {
    companion object {
        private val WHITESPACE = " "
        private val DASH = "-"
        private val SLASH = "/"

        const val USERNAME_VALID: Int = 0
        const val USERNAME_EMPTY: Int = -1
        const val USERNAME_CONTAINS_INVALID_CHAR: Int = -2

        fun isEmptyOrBlankOrNull(string: String): Boolean {
            return (string.isEmpty() || string.isNullOrBlank())
        }

        fun isUsernameValid(username: String): Int {
            return if (isEmptyOrBlankOrNull(username)) {
                USERNAME_EMPTY
            } else if (username.contains(WHITESPACE) || username.contains(DASH) || username.contains(SLASH)) {
                USERNAME_CONTAINS_INVALID_CHAR
            } else {
                USERNAME_VALID
            }
        }

        fun isValidToGetRecommendation(username: String, location: String, weather: String): Boolean {
            val usernameValid = isUsernameValid(username) == USERNAME_VALID
            val locationValid = !isEmptyOrBlankOrNull(location)
            val weatherValid = !isEmptyOrBlankOrNull(weather)

            return (usernameValid && locationValid && weatherValid)
        }
    }
}