package org.koenighotze.bodleian.book

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.koenighotze.bodleian.book.Book.Companion.randomId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

//TODO mit testcontainer
@DataJpaTest
class BookRepositoryTest(@Autowired var repository: BookRepository) {
    @Test
    fun `The repository returns an empty list of books`() {
        assertThat(repository.findAll()).isEmpty()
    }

    fun `a stored book is returned`() {
        repository.save(Book(title = "Some title", authors = "Some author", id = randomId()))

        assertThat(repository.findAll()).isNotEmpty
    }
}