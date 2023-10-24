package org.koenighotze.bodleian.bookcatalog

import org.koenighotze.bodleian.bookcatalog.entity.Author
import org.koenighotze.bodleian.bookcatalog.entity.AuthorsGroup
import org.koenighotze.bodleian.bookcatalog.entity.Book
import org.koenighotze.bodleian.bookcatalog.entity.ISBN
import java.util.*
import java.util.UUID.randomUUID

object DomainTestDataHelper {
    fun randomBook(): Book {
        return Book(
            isbn = ISBN(randomUUID().toString()),
            title = "Random Title ${randomUUID()}",
            id = Book.randomId()
        ).withAuthorsGroup(randomAuthorsGroup())
    }

    fun randomAuthorsGroup() = AuthorsGroup(id = AuthorsGroup.randomId()).withAuthors(
        randomAuthors()
    )

    fun randomAuthors() = 1.until(Random().nextInt(1, 4)).map { randomAuthor() }.toMutableSet()

    fun randomAuthor() = Author(
        firstName = "Random first name ${randomUUID()}",
        lastName = "Random last name ${randomUUID()}",
        id = Author.randomId()
    )
}