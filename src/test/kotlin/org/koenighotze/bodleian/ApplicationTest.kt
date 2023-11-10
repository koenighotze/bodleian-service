package org.koenighotze.bodleian

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.koenighotze.bodleian.bookcatalog.BookRepository
import org.koenighotze.bodleian.bookcatalog.entity.Book
import org.springframework.context.ApplicationContext

class ApplicationTest {
    @Test
    fun `when generating a random book, the book, authorgroup and authors are filled`() {
        val book = Application().randomBook()

        with(book) {
            assertThat(isbn).isNotNull
            assertThat(title).isNotNull
            assertThat(authorsGroup).isNotNull
            assertThat(authorsGroup.authors).isNotEmpty()
        }
    }

    @Test
    fun `when building the cli runner, it should return a function that bootstraps the books`() {
        val bookRepository = mockk<BookRepository>()
        val ctx = mockk<ApplicationContext>()
        every {
            ctx.beanDefinitionNames
        } returns arrayOf("foo", "bar")
        every {
            bookRepository.saveAll(any<List<Book>>())
        } returns emptyList()

        val runner = Application().cliRunner(ctx, bookRepository)

        runner.run("qux")

        verify(exactly = 1) {
            bookRepository.saveAll(any<List<Book>>())
        }
    }


}