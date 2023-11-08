package org.koenighotze.bodleian.bookcatalog.entity.jpa

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.koenighotze.bodleian.bookcatalog.entity.OpenBookApiId

class OpenBookApiIdStringConverterTest {
    @Test
    fun `when converting to database column then the code is returned`() {
        val converter = OpenBookApiIdStringConverter()

        val result = converter.convertToDatabaseColumn(OpenBookApiId("foo"))

        assertThat(result).isEqualTo("foo")
    }

    @Test
    fun `when converting null to database column then null is returned`() {
        val converter = OpenBookApiIdStringConverter()

        val result = converter.convertToDatabaseColumn(null)

        assertThat(result).isNull()
    }

    @Test
    fun `when converting to entity attribute then the code is returned`() {
        val converter = OpenBookApiIdStringConverter()

        val result = converter.convertToEntityAttribute("foo")

        assertThat(result).isEqualTo(OpenBookApiId("foo"))
    }

    @Test
    fun `when converting null to entity attribute then null is returned`() {
        val converter = OpenBookApiIdStringConverter()

        val result = converter.convertToEntityAttribute(null)

        assertThat(result).isNull()
    }
}