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
	router.GET("/books/:id", getBookById)
	router.POST("/books", addBook)
	router.Run("localhost:8080")
}

func getBooks(c *gin.Context) {
	c.IndentedJSON(http.StatusOK, books)
}

func getBookById(c *gin.Context) {
	id := c.Param("id")
	fmt.Println("Searching for", id)
	for _, book := range books {
		if book.ID == id {
			c.IndentedJSON(http.StatusOK, book)
			return
		}
	}

	c.IndentedJSON(http.StatusNotFound, nil)
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
