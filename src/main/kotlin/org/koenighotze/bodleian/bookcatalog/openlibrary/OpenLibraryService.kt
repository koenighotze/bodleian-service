package org.koenighotze.bodleian.bookcatalog.openlibrary

import org.koenighotze.bodleian.bookcatalog.entity.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.*
import java.util.Optional.empty
import java.util.Optional.of

@Service
class OpenLibraryService(private val resilientOpenLibraryRestTemplate: RestTemplate) {
    companion object {
        private val logger = LoggerFactory.getLogger(OpenLibraryService::class.java)
    }

    private fun openLibraryAuthorToBookAuthor(openLibraryAuthor: OpenLibraryAuthor?): MutableSet<Author> {
        if (null == openLibraryAuthor) {
            return mutableSetOf()
        }

        val names = openLibraryAuthor.name.split(" ")
        return mutableSetOf(Author(firstName = names[0], lastName = names[1], id = Author.randomId()))
    }

    fun fetchAuthorsFromOpenBookAuthorKey(authorKey: AuthorKey): MutableSet<Author> {
        val response = resilientOpenLibraryRestTemplate.getForEntity(
            "${authorKey.key}.json",
            OpenLibraryAuthor::class.java
        )
        return openLibraryAuthorToBookAuthor(response.body)
    }

    fun fetchBookDefinitionFromOpenBook(isbn: ISBN): Optional<Book> {
        val openLibraryBookResponse = resilientOpenLibraryRestTemplate.getForEntity(
            "/isbn/${isbn.code}.json",
            OpenLibraryBook::class.java
        ) // TODO: handle exception

        if (null == openLibraryBookResponse.body) {
            return empty()
        }
        logger.trace("Found book data {}", openLibraryBookResponse.toString())

        val authorKeys = openLibraryBookResponse.body
            ?.authors
            ?.map { fetchAuthorsFromOpenBookAuthorKey(it) }
            ?.firstOrNull()

        val book =
            openLibraryBookResponse.body!!.toBook(
                authorsGroup = if (null == authorKeys) AuthorsGroup(id = AuthorsGroupId()) else AuthorsGroup(
                    authors = authorKeys,
                    id = AuthorsGroupId()
                )
            )
        return of(book)
    }

}