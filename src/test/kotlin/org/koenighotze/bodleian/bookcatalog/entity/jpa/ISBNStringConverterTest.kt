package org.koenighotze.bodleian.bookcatalog.entity.jpa

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.koenighotze.bodleian.bookcatalog.entity.ISBN

class ISBNStringConverterTest {
    @Test
    fun `when converting to database column then the code is returned`() {
        val converter = ISBNStringConverter()
        val isbn = ISBN("1234567890")

        val result = converter.convertToDatabaseColumn(isbn)

        assertThat(result).isEqualTo("1234567890")
    }

    @Test
    fun `when converting to database column then null is returned`() {
        val converter = ISBNStringConverter()

        val result = converter.convertToDatabaseColumn(null)

        assertThat(result).isNull()
    }

    @Test
    fun `when converting to the entity attribute then the isbn is returned`() {
        val converter = ISBNStringConverter()

        val result = converter.convertToEntityAttribute("1234567890")

        assertThat(result).isEqualTo(ISBN("1234567890"))
    }

    @Test
    fun `when converting to the entity attribute then null is returned`() {
        val converter = ISBNStringConverter()

        val result = converter.convertToEntityAttribute(null)

        assertThat(result).isNull()
    }
}