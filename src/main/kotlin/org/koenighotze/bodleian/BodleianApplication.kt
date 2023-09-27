package org.koenighotze.bodleian

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BodleianApplication

fun main(args: Array<String>) {
    runApplication<BodleianApplication>(*args)
}
