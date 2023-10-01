package org.koenighotze.bodleian.book

import org.koenighotze.bodleian.Manager

@Manager
class BookManager(private val repository: BookRepository) {
    fun allBooks(): Collection<Book> = repository.findAll().toList()
}