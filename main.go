package main

import (
	"fmt"
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/google/uuid"
)

type book struct {
	ID      string
	Title   string
	Authors []string
	Isbn    string
}

var books = []book{
	{
		Title:   "foo",
		Authors: []string{"a", "b"},
		Isbn:    "",
		ID:      "123"}}

func main() {
	router := gin.Default()
	router.GET("/books", getBooks)
	router.GET("/books/:id", getBookByID)
	router.POST("/books", addBook)
	router.DELETE("/books/:id", deleteBook)
	router.Run()
}

func getBooks(c *gin.Context) {
	fmt.Println("Books:", books)
	c.IndentedJSON(http.StatusOK, books)
}

func getBookByID(c *gin.Context) {
	id := c.Param("id")
	fmt.Println("Searching for", id)
	for _, book := range books {
		if book.ID == id {
			c.IndentedJSON(http.StatusOK, book)
			return
		}
	}

	c.IndentedJSON(http.StatusNotFound, gin.H{"message": "book not found"})
}

func addBook(c *gin.Context) {
	var newBook book

	if err := c.BindJSON(&newBook); err != nil {
		c.IndentedJSON(http.StatusBadRequest, nil)
		return
	}

	if newBook.ID == "" {
		newBook.ID = uuid.NewString()
	}
	books = append(books, newBook)
	c.IndentedJSON(http.StatusCreated, newBook)
}

func deleteBook(c *gin.Context) {
	id := c.Param("id")

	var newBooks []book
	deleted := false
	for i, book := range books {
		if book.ID == id {
			fmt.Println("Removing item number", i)
			deleted = true
			c.Status(http.StatusOK)
			continue
		}
		fmt.Println("Adding id ", books[i].ID, "to new books")
		newBooks = append(newBooks, books[i])
	}
	books = newBooks

	if !deleted {
		c.Status(http.StatusNotFound)
	}

}
