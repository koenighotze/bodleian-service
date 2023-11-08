package org.koenighotze.bodleian.bookcatalog.entity

/**
 * ISBN value object.
 *
 * @param code the ISBN code
 */
data class ISBN(val code: String) {
    companion object {
        /**
         * Checks if the given code is a valid ISBN.
         *
         * @return true if the code is a valid ISBN, false otherwise
         */
        fun isValid(code: String) = try {
            when (code.length) {
                10 -> isValidISBN10(code)
                13 -> isValidISBN13(code)
                else -> false
            }
        } catch (e: NumberFormatException) {
            false
        }

        /**
         * Creates a new ISBN instance from the given code.
         * @throws IllegalArgumentException if the code is not a valid ISBN
         */
        fun of(code: String): ISBN = if (isValid(code)) ISBN(code) else throw IllegalArgumentException("Invalid ISBN: $code")

        private fun isValidISBN13(code: String): Boolean = code.mapIndexed { index, c ->
                val value = c.toString().toInt()
                if (index % 2 == 0) value else value * 3
            }.sum()% 10 == 0

        private fun isValidISBN10(code: String) = code.mapIndexed { index, c ->
                val value = c.toString().toInt()
                value * (index + 1)
            }.sum() % 11 == 0
    }

}