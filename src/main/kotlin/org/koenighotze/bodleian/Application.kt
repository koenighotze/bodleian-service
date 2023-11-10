package org.koenighotze.bodleian

import org.koenighotze.bodleian.bookcatalog.BookRepository
import org.koenighotze.bodleian.bookcatalog.entity.Author
import org.koenighotze.bodleian.bookcatalog.entity.AuthorsGroup
import org.koenighotze.bodleian.bookcatalog.entity.Book
import org.koenighotze.bodleian.bookcatalog.entity.Book.Companion.randomId
import org.koenighotze.bodleian.bookcatalog.entity.ISBN
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
    private fun randomAuthorsGroup() = AuthorsGroup(
        id = AuthorsGroup.randomId()
    ).withAuthors(
        setOf(
            Author(
                firstName = "Random first name ${randomUUID()}",
                lastName = "Random last name ${randomUUID()}",
                id = Author.randomId()
            )
        )
    )

    fun randomBook() = Book(
        isbn = ISBN(code = randomUUID().toString()),
        title = "Random Title ${randomUUID()}",
        id = randomId()
    ).withAuthorsGroup(randomAuthorsGroup())

    @Bean
    fun cliRunner(ctx: ApplicationContext, bookRepository: BookRepository): CommandLineRunner {
        return CommandLineRunner { args ->
            args.forEach { logger.trace(it) }
            ctx.beanDefinitionNames.sorted().forEach { logger.trace(it) }

            logger.trace("Generating crappy random books")
            val entities = (1..10).map { randomBook() }
            bookRepository.saveAll(entities)
        }

    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

