package org.koenighotze.library

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@Tag("integration")
internal class DbLoaderTest(@Autowired val repository: BooksRepository, @Autowired val dbLoader: DbLoader): WordSpec({
    "The DBLoader" should {
        "load the books into the repository" {
            val books = withContext(Dispatchers.IO) { repository.findAll() }

            books.size shouldBeGreaterThan 2
            books.find { it.title == ("Release IT!") } shouldNotBe null
        }
    }
})
