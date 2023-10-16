package org.koenighotze.bodleian.bookshelf.entity

import org.koenighotze.bodleian.user.entity.UserId

typealias BookshelfId = String

class Bookshelf(var id: BookshelfId?, var owner: UserId, var bookshelfItems: Set<BookshelfItem>)