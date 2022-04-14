package org.koenighotze.library

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LibraryApplication

fun main(args: Array<String>) {
	@Suppress("SpreadOperator")
	runApplication<LibraryApplication>(*args)
}
