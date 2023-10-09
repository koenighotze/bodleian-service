package org.koenighotze.bodleian.book.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import java.util.UUID.randomUUID

@Entity
@Table(name = "AUTHORS")
class Author(
    @ManyToMany(mappedBy = "authors") var authorsGroup: MutableSet<AuthorsGroup> = mutableSetOf(),
    var firstName: String, var lastName: String, @Id var id: String? = null
) {
    companion object {
        fun randomId() = randomUUID().toString()
    }

    override fun toString(): String {
        return "Author(firstName='$firstName', lastName='$lastName', id=$id)"
    }


}
