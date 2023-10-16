package org.koenighotze.bodleian.wishlist.entity

import org.koenighotze.bodleian.user.entity.UserId

typealias WishlistId = String

class Wishlist(var id: WishlistId?, var owner: UserId, var items: Set<WishlistItem>)