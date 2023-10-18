package org.koenighotze.bodleian.bookcatalog

import org.koenighotze.bodleian.bookcatalog.entity.Book
import org.springframework.data.repository.CrudRepository

interface BookRepository : CrudRepository<Book, String>