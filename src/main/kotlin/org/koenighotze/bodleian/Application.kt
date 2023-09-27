package org.koenighotze.bodleian

import org.koenighotze.bodleian.book.BookRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {
    @Bean
    fun cliRunner(ctx: ApplicationContext, bookRepository: BookRepository): CommandLineRunner {
        return CommandLineRunner { args ->
            bookRepository.findAll().forEach(::println)

//            args.forEach(::println)
//            ctx.beanDefinitionNames.sorted().forEach(::println)
        }

    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
