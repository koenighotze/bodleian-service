package org.koenighotze.bodleian

import org.koenighotze.bodleian.book.BookRepository
import org.koenighotze.bodleian.book.entity.Author
import org.koenighotze.bodleian.book.entity.AuthorsGroup
import org.koenighotze.bodleian.book.entity.Book
import org.koenighotze.bodleian.book.entity.Book.Companion.randomId
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import java.util.UUID.randomUUID

@SpringBootApplication
class Application {
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
        return CommandLineRunner { _ ->
            bookRepository.findAll().forEach(::println)
//            args.forEach(::println)
//            ctx.beanDefinitionNames.sorted().forEach(::println)

            println("Generating crappy random books")
            val entities = (1..1).map { randomBook() }

            entities.forEach {
                println("Should persists $it")
                bookRepository.save(it)
            }

//            bookRepository.saveAll(entities)
        }

    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

