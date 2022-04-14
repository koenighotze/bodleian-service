package org.koenighotze.library

import org.koenighotze.library.model.Book
import org.springframework.stereotype.Component
import java.util.UUID.randomUUID
import javax.annotation.PostConstruct

@Component
class DbLoader(val repository: BooksRepository) {
    @PostConstruct
    fun initializeBooks() {
        repository.saveAll(
            listOf(
                Book(randomUUID().toString(), "The new Kornshell", "David G. Korn", "Todo"),
                Book(randomUUID().toString(), "Release IT!", "Michael T. Nygard", "Todo"),
                Book(randomUUID().toString(), "Refactoring", "Martin Fowler", "Todo"),
                Book(randomUUID().toString(), "Code Complete", "Steve McConnell", "Todo"),
                Book(randomUUID().toString(), "Land of Lisp", "Conrad Barski", "Todo")
            )
        )
    }
}
