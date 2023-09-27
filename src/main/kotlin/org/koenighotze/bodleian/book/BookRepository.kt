package org.koenighotze.bodleian.book

import org.springframework.data.repository.CrudRepository

interface BookRepository : CrudRepository<Book, String>