// Package books
package books

import (
	"fmt"
	"log"
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/go-http-utils/headers"
	"github.com/google/uuid"
)

// BookService todo
type BookService struct {
	repository BookRepository
}

func (service BookService) getRequestedBook(c *gin.Context) (*Book, error) {
	id := BookID(c.Param("id"))
	book, err := service.repository.GetBookByID(id)
	if err != nil {
		log.Println(err)
		c.AbortWithStatus(http.StatusNotFound)
		return nil, err
	}
	return book, nil
}

func (service BookService) getBookByID(c *gin.Context) {
	book, err := service.getRequestedBook(c)
	if err != nil {
		return
	}
	c.IndentedJSON(http.StatusOK, book)
}

func (service BookService) updateBookByID(c *gin.Context) {
	book, err := service.getRequestedBook(c)
	if err != nil {
		return
	}

	var newBook Book
	if err := c.BindJSON(&newBook); err != nil {
		c.AbortWithStatus(http.StatusBadRequest)
	}

	err = service.repository.UpdateBookByID(book.ID, newBook)

	if err != nil {
		c.AbortWithStatus(http.StatusInternalServerError)
		return
	}
}

func (service BookService) removeBookFromLibraryByID(c *gin.Context) {
	book, err := service.getRequestedBook(c)
	if err != nil {
		return
	}

	err = service.repository.DeleteBookByID(book.ID)
	if err != nil {
		c.AbortWithStatus(http.StatusInternalServerError)
		return
	}
}

func (service BookService) getAllBooks(c *gin.Context) {
	books, err := service.repository.GetAllBooks()
	if err != nil {
		log.Println(err)
		c.AbortWithStatus(http.StatusInternalServerError)
		return
	}

	c.IndentedJSON(http.StatusOK, books)
}

func (service BookService) addNewBookToLibrary(c *gin.Context) {
	var book Book
	if err := c.BindJSON(&book); err != nil {
		log.Println(err)
		c.AbortWithStatus(http.StatusBadRequest)
		return
	}
	book.ID = BookID(uuid.NewString())

	err := service.repository.AddNewBook(book)
	if err != nil {
		log.Println(err)
		c.AbortWithStatus(http.StatusInternalServerError)
		return
	}
	c.Header(headers.Location, fmt.Sprintf("%s/%s", c.Request.URL.Path, book.ID))
	c.Status(http.StatusCreated)
}

func NewBookService(repository BookRepository) BookService {
	return BookService{
		repository: repository,
	}
}
