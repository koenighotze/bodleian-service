package org.koenighotze.bodleian.book

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID.randomUUID


@Entity
@Table(name = "BOOKS")
class Book(var title: String, var authors: String, var isbn: String? = null, @Id var id: String? = null) {
    companion object {
        fun randomId() = randomUUID().toString()
    }
}

// Cover image
// num pages
// price
// ...