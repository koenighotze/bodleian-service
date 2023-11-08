package org.koenighotze.bodleian.bookcatalog

import org.koenighotze.bodleian.bookcatalog.entity.Author
import org.koenighotze.bodleian.bookcatalog.entity.AuthorsGroup
import org.koenighotze.bodleian.bookcatalog.entity.Book
import org.koenighotze.bodleian.bookcatalog.entity.ISBN
import java.util.*
import java.util.UUID.randomUUID

object DomainTestDataHelper {
    fun randomBook() =
        Book(
            isbn = ISBN(randomUUID().toString()),
            title = "Random Title ${randomUUID()}",
            id = Book.randomId()
        ).withAuthorsGroup(randomAuthorsGroup())

    private fun randomAuthorsGroup() = AuthorsGroup(id = AuthorsGroup.randomId()).withAuthors(
        randomAuthors()
    )

    private fun randomAuthors() = 1.until(Random().nextInt(2, 4)).map { randomAuthor() }.toMutableSet()

    private fun randomAuthor() = Author(
        firstName = "Random first name ${randomUUID()}",
        lastName = "Random last name ${randomUUID()}",
        id = Author.randomId()
    )
}