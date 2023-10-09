package org.koenighotze.bodleian.book

import org.koenighotze.bodleian.book.entity.Book
import org.springframework.data.repository.CrudRepository

interface BookRepository : CrudRepository<Book, String>