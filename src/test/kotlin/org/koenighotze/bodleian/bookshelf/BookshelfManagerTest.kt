package org.koenighotze.bodleian.bookshelf

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koenighotze.bodleian.bookcatalog.entity.Book
import org.koenighotze.bodleian.bookshelf.entity.Bookshelf
import java.util.Optional.empty
import java.util.Optional.of

class BookshelfManagerTest {
    val bookshelfRepository = mockk<BookshelfRepository>()
    val bookshelfManager = BookshelfManager(bookshelfRepository)

    @Nested
    @DisplayName("When adding a book to the bookshelf")
    inner class AddBookToBookshelf {
        @Test
        fun `and the bookshelf is found, it should add the book to the bookshelf`() {
            val ownerId = "user123"
            val bookshelf = Bookshelf.forOwner(ownerId)
            val expectedBookId = Book.randomId()
            every { bookshelfRepository.findById(bookshelf.id) } returns of(bookshelf)

            bookshelfManager.addBookToCollection(bookshelf.id, expectedBookId)

            assertThat(bookshelf.bookshelfItems).hasSize(1)
            assertThat(bookshelf.bookshelfItems.first().referenceId).isEqualTo(expectedBookId)

        }

        @Test
        fun `and the bookshelf is not found, it should throw`() {
            val ownerId = "user123"
            val bookshelf = Bookshelf.forOwner(ownerId)
            every { bookshelfRepository.findById(bookshelf.id) } returns empty()


            assertThrows<IllegalArgumentException> {
                bookshelfManager.addBookToCollection(bookshelf.id, Book.randomId())
            }
        }
    }

    @Nested
    @DisplayName("When removing a book from the bookshelf")
    inner class RemoveBookFromBookshelf

    @Nested
    @DisplayName("When getting the bookshelf")
    inner class GettingTheBookshelf {
        @Test
        fun `and the bookshelf is found, it should return the bookshelf`() {
            val ownerId = "user123"
            val expected = Bookshelf.forOwner(ownerId)
            every { bookshelfRepository.findByOwner(ownerId) } returns of(expected)

            val result = bookshelfManager.getBookshelfForOwner(ownerId)

            assertThat(result.get()).isEqualTo(expected)
        }

        @Test
        fun `and the bookshelf is not found, it should create new bookshelf`() {
            val ownerId = "user123"
            val bookshelfId = "bookshelf123"
            val ownerBookshelf = Bookshelf(bookshelfId, ownerId)
            val expectedBookshelf = Bookshelf(bookshelfId, ownerId)

            every { bookshelfRepository.findByOwner(ownerId) } returns empty()
            every { bookshelfRepository.save(any()) } returns ownerBookshelf

            val result = bookshelfManager.getBookshelfForOwner(ownerId)

            with(result.get()) {
                assertThat(ownerId).isEqualTo(expectedBookshelf.owner)
                assertThat(id).isNotNull()
                assertThat(bookshelfItems).isEmpty()
            }
            verify(exactly = 1) { bookshelfRepository.findByOwner(ownerId) }
            verify(exactly = 1) { bookshelfRepository.save(any()) }
        }
    }

}