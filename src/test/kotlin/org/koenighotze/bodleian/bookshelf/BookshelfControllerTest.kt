
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.koenighotze.bodleian.bookshelf.BookshelfController
import org.koenighotze.bodleian.bookshelf.BookshelfManager
import org.koenighotze.bodleian.bookshelf.entity.Bookshelf
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.OK
import java.util.Optional.of

@Nested
@DisplayName("BookshelfController")
class BookshelfControllerTest {
    private val bookshelfManager = mockk<BookshelfManager>()
    private val controller = BookshelfController(bookshelfManager)

    @Nested
    @DisplayName("when adding a book to the bookshelf")
    inner class AddingABookToTheBookshelf {
        @Test
        fun `and the book is not in the bookshelf yet, should return OK`() {
            val bookshelfId = "bookshelf123"
            val bookId = "book123"
            every { bookshelfManager.addBookToCollection(bookshelfId, bookId) } just Runs

            val response = controller.addBookToBookshelf(bookshelfId, bookId)

            assertThat(response.statusCode).isEqualTo(OK)
        }

        @Test
        fun `and an error occurs, should return INTERNAL_SERVER_ERROR`() {
            val bookshelfId = "bookshelf123"
            val bookId = "book123"
            every { bookshelfManager.addBookToCollection(bookshelfId, bookId) } throws RuntimeException("Error")

            val response = controller.addBookToBookshelf(bookshelfId, bookId)

            assertThat(response.statusCode).isEqualTo(INTERNAL_SERVER_ERROR)
        }
    }

    @Nested
    @DisplayName("when removing a book from the bookshelf")
    inner class RemovingABookFromTheBookshelf {
        @Test
        fun `and the book is in the bookshelf, should return OK`() {
            val bookshelfId = "bookshelf123"
            val bookId = "book123"
            every { bookshelfManager.removeBookFromCollection(bookshelfId, bookId) } just Runs

            val response = controller.removeBookFromBookshelf(bookshelfId, bookId)

            assertThat(response.statusCode).isEqualTo(OK)
        }

        @Test
        fun `and an error occurs, should return INTERNAL_SERVER_ERROR`() {
            val bookshelfId = "bookshelf123"
            val bookId = "book123"
            every { bookshelfManager.removeBookFromCollection(bookshelfId, bookId) } throws RuntimeException("Error")

            val response = controller.removeBookFromBookshelf(bookshelfId, bookId)

            assertThat(response.statusCode).isEqualTo(INTERNAL_SERVER_ERROR)
        }
    }

    @Nested
    @DisplayName("when getting the bookshelf for an owner")
    inner class GettingTheBookshelfForAnOwner {
        @Test
        fun `and the bookshelf is found, should return OK`() {
            val ownerId = "user123"
            val bookshelf = Bookshelf.forOwner(ownerId)
            every { bookshelfManager.getBookshelfForOwner(ownerId) } returns of(bookshelf)

            val response = controller.getBookshelfForOwner(ownerId)

            assertThat(response.statusCode).isEqualTo(OK)
            assertThat(response.body).isEqualTo(bookshelf)
        }

        @Test
        fun `and an error occurs, should return INTERNAL_SERVER_ERROR`() {
            val ownerId = "user123"
            every { bookshelfManager.getBookshelfForOwner(ownerId) } throws RuntimeException("Error")

            val response = controller.getBookshelfForOwner(ownerId)

            assertThat(response.statusCode).isEqualTo(INTERNAL_SERVER_ERROR)
        }
    }
}