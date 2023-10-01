package org.koenighotze.bodleian

import org.koenighotze.bodleian.book.Book
import org.koenighotze.bodleian.book.Book.Companion.randomId
import org.koenighotze.bodleian.book.BookRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import java.util.UUID.randomUUID

@SpringBootApplication
class Application {
    fun randomBook() = Book(
        isbn = randomUUID().toString(),
        title = "Random Title ${randomUUID()}",
        authors = "Random authors ${randomUUID()}",
        id = randomId()
    )

    @Bean
    fun cliRunner(ctx: ApplicationContext, bookRepository: BookRepository): CommandLineRunner {
        return CommandLineRunner { args ->
            bookRepository.findAll().forEach(::println)
//            args.forEach(::println)
//            ctx.beanDefinitionNames.sorted().forEach(::println)

            println("Generating crappy random books")
            val entities = (1..10).map { randomBook() }

            bookRepository.saveAll(entities)
        }

    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
