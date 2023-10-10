package org.koenighotze.bodleian

import org.koenighotze.bodleian.book.BookRepository
import org.koenighotze.bodleian.book.entity.Author
import org.koenighotze.bodleian.book.entity.AuthorsGroup
import org.koenighotze.bodleian.book.entity.Book
import org.koenighotze.bodleian.book.entity.Book.Companion.randomId
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import java.util.UUID.randomUUID

@SpringBootApplication
class Application {
    companion object {
        var logger: Logger = LoggerFactory.getLogger(Application::class.java)
    }

    // TODO Bootstrap not with random data
    fun randomAuthorsGroup() = AuthorsGroup(
        id = AuthorsGroup.randomId(),
        books = mutableSetOf(),
        authors = mutableSetOf(
            Author(
                firstName = "Random first name ${randomUUID()}",
                lastName = "Random last name ${randomUUID()}",
                id = Author.randomId()
            )
        )
    )

    fun randomBook() = Book(
        isbn = randomUUID().toString(),
        title = "Random Title ${randomUUID()}",
        id = randomId()
    ).withAuthorsGroup(randomAuthorsGroup())

    @Bean
    fun cliRunner(ctx: ApplicationContext, bookRepository: BookRepository): CommandLineRunner {
        return CommandLineRunner { args ->
            args.forEach { logger.debug(it) }
            ctx.beanDefinitionNames.sorted().forEach { logger.debug(it) }

            logger.debug("Generating crappy random books")
            val entities = (1..10).map { randomBook() }
            bookRepository.saveAll(entities)
        }

    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

