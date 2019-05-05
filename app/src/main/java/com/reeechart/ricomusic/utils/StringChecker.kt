package com.reeechart.ricomusic.utils

/**
 * Created by Reeechart on 06-May-19.
 */
class StringChecker {
    companion object {
        private val WHITESPACE = " "
        private val DASH = "-"
        private val SLASH = "/"

        val USERNAME_VALID: Int = 0
        val USERNAME_EMPTY: Int = -1
        val USERNAME_CONTAINS_INVALID_CHAR: Int = -2

        fun isUsernameValid(username: String): Int {
            return if (username.isNullOrBlank() || username.isEmpty()) {
                USERNAME_EMPTY
            } else if (username.contains(WHITESPACE) || username.contains(DASH) || username.contains(SLASH)) {
                USERNAME_CONTAINS_INVALID_CHAR
            } else {
                USERNAME_VALID
            }
        }
    }
}