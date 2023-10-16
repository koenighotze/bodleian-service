package org.koenighotze.bodleian.wishlist.entity

import org.koenighotze.bodleian.book.entity.BookId

typealias WishlistItemId = String

class WishlistItem(
    var id: WishlistItemId?,
    var referenceId: BookId,
    var rating: WishlistItemRating
)
