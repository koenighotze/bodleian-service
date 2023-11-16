package org.koenighotze.bodleian.bookshelf.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BookshelfItemTest {
    @Test
    fun `creating new BookshelfItem with valid BookId should set referenceId property correctly`() {
        val bookId = "123"

        val bookshelfItem = BookshelfItem.of(bookId)

        assertThat(bookshelfItem.referenceId).isEqualTo(bookId)
        assertThat(bookshelfItem.id).isNotNull()
    }

}