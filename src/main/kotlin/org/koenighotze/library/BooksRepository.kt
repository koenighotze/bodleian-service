package org.koenighotze.library

import org.koenighotze.library.model.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BooksRepository : JpaRepository<Book, String> {
}
