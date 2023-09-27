package org.koenighotze.bodleian.book

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "BOOKS")
class Book(var title: String, var authors: String, var isbn: String? = null, @Id var id: String? = null)

// Cover image
// num pages
// price
// ...