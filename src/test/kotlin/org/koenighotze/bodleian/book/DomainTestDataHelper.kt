package org.koenighotze.bodleian.book

import org.koenighotze.bodleian.book.entity.Author
import org.koenighotze.bodleian.book.entity.AuthorsGroup
import org.koenighotze.bodleian.book.entity.Book
import java.util.*

object DomainTestDataHelper {
    fun randomBook() = Book(
        isbn = UUID.randomUUID().toString(),
        title = "Random Title ${UUID.randomUUID()}",
        authorsGroup = randomAuthorsGroup(),
        id = Book.randomId()
    )

    fun randomAuthorsGroup() = AuthorsGroup(id = AuthorsGroup.randomId(), authors = randomAuthors())

    fun randomAuthors() = 1.until(Random().nextInt(1, 4)).map { randomAuthor() }.toMutableSet()

    fun randomAuthor() = Author(
        firstName = "Random first name ${UUID.randomUUID()}",
        lastName = "Random last name ${UUID.randomUUID()}",
        id = Author.randomId()
    )
}