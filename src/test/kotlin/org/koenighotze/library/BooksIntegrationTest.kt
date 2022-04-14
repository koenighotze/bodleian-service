package org.koenighotze.library

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.DisplayName
import org.koenighotze.library.model.Book
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpMethod.DELETE
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity

@SpringBootTest(
    classes = [LibraryApplication::class],
    webEnvironment = RANDOM_PORT
)
@DisplayName("The Books REST endpoint")
@IntegrationTestTag
class BooksIntegrationTest(
    @Autowired val template: TestRestTemplate,
    @Autowired val repo: BooksRepository
) : BehaviorSpec({
    beforeTest {
        withContext(Dispatchers.IO) {
            repo.saveAll(WellKnownBooks.books)
        }
    }

    afterTest {
        withContext(Dispatchers.IO) {
            repo.deleteAll()
        }
    }

    given("The Books endpoint") {
        `when`("GET-ing a book") {
            and("the book is found") {
                then("it should return the book's data") {
                    val wellKnownId = WellKnownBooks.books[0].id
                    val response = template.getForEntity("/books/$wellKnownId", Book::class.java)

                    assertSoftly {
                        response.statusCode shouldBe OK
                        response.body shouldBe WellKnownBooks.books[0]
                    }
                }
            }
            and("the book is not found") {
                then("it should return NOT_FOUND") {
                    val response = template.getForEntity("/books/not_there", Book::class.java)

                    response.statusCode shouldBe NOT_FOUND
                }
            }
        }

        `when`("DELETE-ing a book") {
            and("the book is found") {
                then("it should return 200") {
                    val wellKnownId = WellKnownBooks.books[0].id
                    val response = template.exchange("/books/$wellKnownId", DELETE, null, ResponseEntity::class.java)

                    response.statusCode shouldBe OK
                }
            }
            and("the book is not found") {
                then("it should return 404") {
                    val response = template.exchange("/books/not_there", DELETE, null, ResponseEntity::class.java)

                    response.statusCode shouldBe NOT_FOUND
                }
            }
        }
    }
})
