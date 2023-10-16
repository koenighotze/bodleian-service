package org.koenighotze.bodleian.bookshelf.entity

import org.koenighotze.bodleian.book.entity.BookId

typealias BookshelfItemId = String

class BookshelfItem(
    var id: BookshelfItemId?,
    var referenceId: BookId
)
