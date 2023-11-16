package org.koenighotze.bodleian.bookshelf.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BookshelfTest {
    @Test
    fun `Given a new Bookshelf, when creating with a random ID and owner, then the Bookshelf should be created with the provided ID and owner`() {
        // Given
        val randomId = Bookshelf.randomId()
        val owner = "user123"

        // When
        val bookshelf = Bookshelf(randomId, owner)

        // Then
        assertThat(bookshelf.id).isEqualTo(randomId)
        assertThat(bookshelf.owner).isEqualTo(owner)
    }

    @Test
    fun `Given a Bookshelf, when adding a BookshelfItem, then the BookshelfItem should be added to the Bookshelf`() {
        // Given
        val bookshelf = Bookshelf(Bookshelf.randomId(), "user123")
        val bookshelfItem = BookshelfItem.of("book123")

        // When
        bookshelf.addBookshelfItem(bookshelfItem)

        // Then
        assertThat(bookshelf.bookshelfItems).contains(bookshelfItem)
    }

    @Test
    fun `Given a Bookshelf with a BookshelfItem, when removing the BookshelfItem, then the BookshelfItem should be removed from the Bookshelf`() {
        // Given
        val bookshelf = Bookshelf(Bookshelf.randomId(), "user123")
        val bookshelfItem = BookshelfItem.of("book123")
        bookshelf.addBookshelfItem(bookshelfItem)

        // When
        val removed = bookshelf.removeBookshelfItem(bookshelfItem)

        // Then
        assertThat(removed).isTrue()
        assertThat(bookshelf.bookshelfItems).doesNotContain(bookshelfItem)
    }

    @Test
    fun `Given a Bookshelf with multiple BookshelfItems, when retrieving all BookshelfItems, then all BookshelfItems should be returned`() {
        // Given
        val bookshelf = Bookshelf(Bookshelf.randomId(), "user123")
        val bookshelfItem1 = BookshelfItem.of("book123")
        val bookshelfItem2 = BookshelfItem.of("book456")
        bookshelf.addBookshelfItem(bookshelfItem1)
        bookshelf.addBookshelfItem(bookshelfItem2)

        // When
        val bookshelfItems = bookshelf.bookshelfItems

        // Then
        assertThat(bookshelfItems).containsExactly(bookshelfItem1, bookshelfItem2)
    }

    @Test
    fun `Given a Bookshelf, when removing a non-existent BookshelfItem, then the Bookshelf should not remove any BookshelfItem`() {
        // Given
        val bookshelf = Bookshelf(Bookshelf.randomId(), "user123")
        val bookshelfItem = BookshelfItem.of("book123")

        // When
        val removed = bookshelf.removeBookshelfItem(bookshelfItem)

        // Then
        assertThat(removed).isFalse()
        assertThat(bookshelf.bookshelfItems).isEmpty()
    }

}