package org.koenighotze.library

import io.kotest.core.spec.style.WordSpec
import org.junit.jupiter.api.Tag
import org.springframework.boot.test.context.SpringBootTest

@Tag("integration")
@SpringBootTest
class LibraryApplicationIntegrationTest: WordSpec({
	"The application" should {
		"load its context" {
			// nothing to do
		}
	}
})
