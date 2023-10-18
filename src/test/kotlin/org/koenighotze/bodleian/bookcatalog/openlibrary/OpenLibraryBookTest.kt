package org.koenighotze.bodleian.bookcatalog.openlibrary

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koenighotze.bodleian.bookcatalog.entity.ISBN

class OpenLibraryBookTest {

    @DisplayName("When getting the ISBN")
    @Nested
    inner class WhenGettingTheISBN {
        private val book = OpenLibraryBook(
            title = "",
            authors = arrayListOf(),
            isbn10 = arrayListOf(),
            isbn13 = arrayListOf(),
            covers = arrayListOf(),
            key = ""
        )

        @DisplayName("When getting the ISBN")
        @Nested
        inner class AndAnIsbn10IsAvailable {
            @Test
            fun `should return the isbn10`() {
                val expectedIsbn = ISBN("1234567890")
                book.isbn10.add(expectedIsbn.code)

                val result = book.isbn

                assertThat(result).isEqualTo(expectedIsbn)
            }
        }

        @DisplayName("When getting the ISBN")
        @Nested
        inner class AndAnIsbn13IsAvailable {
            @Test
            fun `and no isbn10 is provided, should return the isbn13`() {
                val expectedIsbn = ISBN("1234567890")
                book.isbn13.add(expectedIsbn.code)

                val result = book.isbn

                assertThat(result).isEqualTo(expectedIsbn)
            }

            @Test
            fun `and a isbn10 is provided, should return the isbn10`() {
                val expectedIsbn = ISBN("1234567890")
                book.isbn10.add(expectedIsbn.code)
                book.isbn13.add("something else")

                val result = book.isbn

                assertThat(result).isEqualTo(expectedIsbn)
            }
        }

        @DisplayName("When getting the ISBN")
        @Nested
        inner class AndNoIsbnIsAvailable {
            @Test
            fun `should return null`() {
                book.isbn10.clear()
                book.isbn13.clear()

                val result = book.isbn

                assertThat(result).isNull()
            }
        }


    }
}