package org.koenighotze.bodleian.bookcatalog.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ISBNTest {
    @Test
    fun `when the isbn is a valid isbn 10 it should return true`() {
        val result = ISBN.isValid("0140328726")

        assertThat(result).isTrue()
    }

    @Test
    fun `when the isbn is a valid isbn 13 it should return true`() {
        val result = ISBN.isValid("9780140328721")

        assertThat(result).isTrue()
    }


    @Test
    fun `when the isbn is neither a valid isbn10 nor a valid isbn13 it should return false`() {
        val result = ISBN.isValid("1234567890")

        assertThat(result).isFalse()
    }

    @Test
    fun `when the isbn contains a non-digit it should return false`() {
        val result = ISBN.isValid("123456789a")

        assertThat(result).isFalse()
    }
}