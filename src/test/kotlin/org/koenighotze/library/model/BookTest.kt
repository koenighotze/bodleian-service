package org.koenighotze.library.model

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class BookTest : WordSpec({
    "A Book" When {
        "constructed" should {
            "allow the ISBN to be optional" {
                val book = Book(id = "123", title = "foo", author = "bar", isbn = null)

                book.isbn shouldBe null
            }
        }
    }
})
