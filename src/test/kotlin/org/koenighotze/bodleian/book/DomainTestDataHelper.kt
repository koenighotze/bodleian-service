package org.koenighotze.bodleian.book

import org.koenighotze.bodleian.book.entity.Author
import org.koenighotze.bodleian.book.entity.AuthorsGroup
import org.koenighotze.bodleian.book.entity.Book
import org.koenighotze.bodleian.book.entity.ISBN
import java.util.*
import java.util.UUID.randomUUID

object DomainTestDataHelper {
    fun randomBook() = Book(
        isbn = ISBN(randomUUID().toString()),
        title = "Random Title ${randomUUID()}",
        authorsGroup = randomAuthorsGroup(),
        id = Book.randomId()
    )

    fun randomAuthorsGroup() = AuthorsGroup(id = AuthorsGroup.randomId(), authors = randomAuthors())

    fun randomAuthors() = 1.until(Random().nextInt(1, 4)).map { randomAuthor() }.toMutableSet()

    fun randomAuthor() = Author(
        firstName = "Random first name ${randomUUID()}",
        lastName = "Random last name ${randomUUID()}",
        id = Author.randomId()
    )
}