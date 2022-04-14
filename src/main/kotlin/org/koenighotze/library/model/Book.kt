package org.koenighotze.library.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Book(@Id val id: String?, val title: String, val author: String, val isbn: String?) {
}
