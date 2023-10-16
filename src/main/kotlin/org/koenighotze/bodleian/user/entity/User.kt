package org.koenighotze.bodleian.user.entity

typealias UserId = String

class User(var id: UserId?, var loginName: String, var primaryEmail: String)