package org.koenighotze.bodleian.book

import org.koenighotze.bodleian.Manager
import org.koenighotze.bodleian.book.entity.Book
import java.util.*

@Manager
class BookManager(private val repository: BookRepository) {
    fun allBooks(): Collection<Book> = repository.findAll().toList()
    fun getBookById(bookId: String): Optional<Book> = repository.findById(bookId)
    fun deleteBook(book: Book): Boolean {
        repository.delete(book)
        return true
    }
}