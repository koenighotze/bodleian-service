package books

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"github.com/go-http-utils/headers"
	"github.com/google/uuid"
	"log"
	"net/http"
)

func getRequestedBook(c *gin.Context) (*Book, error) {
	id := BookID(c.Param("id"))
	book, err := GetBookByID(id)
	if err != nil {
		log.Println(err)
		c.AbortWithStatus(http.StatusNotFound)
		return nil, err
	}
	return book, nil
}

func getBookByID(c *gin.Context) {
	book, err := getRequestedBook(c)
	if err != nil {
		return
	}
	c.IndentedJSON(http.StatusOK, book)
}

func updateBookByID(c *gin.Context) {
	book, err := getRequestedBook(c)
	if err != nil {
		return
	}

	var newBook Book
	if err := c.BindJSON(&newBook); err != nil {
		c.AbortWithStatus(http.StatusBadRequest)
	}

	err = UpdateBookByID(book.ID, newBook)

	if err != nil {
		c.AbortWithStatus(http.StatusInternalServerError)
		return
	}
}

func removeBookFromLibraryByID(c *gin.Context) {
	book, err := getRequestedBook(c)
	if err != nil {
		return
	}

	err = DeleteBookByID(book.ID)
	if err != nil {
		c.AbortWithStatus(http.StatusInternalServerError)
		return
	}
}

func getAllBooks(c *gin.Context) {
	books, err := GetAllBooks()
	if err != nil {
		log.Println(err)
		c.AbortWithStatus(http.StatusInternalServerError)
		return
	}

	c.IndentedJSON(http.StatusOK, books)
}

func addNewBookToLibrary(c *gin.Context) {
	var book Book
	if err := c.BindJSON(&book); err != nil {
		log.Println(err)
		c.AbortWithStatus(http.StatusBadRequest)
		return
	}
	book.ID = BookID(uuid.NewString())

	err := AddNewBook(book)
	if err != nil {
		log.Println(err)
		c.AbortWithStatus(http.StatusInternalServerError)
		return
	}
	c.Header(headers.Location, fmt.Sprintf("%s/%s", c.Request.URL.Path, book.ID))
	c.Status(http.StatusCreated)
}

func booksHandler(group *gin.RouterGroup) {
	group.GET("", getAllBooks)
	group.POST("", addNewBookToLibrary)
}

func bookHandler(group *gin.RouterGroup) {
	group.GET("", getBookByID)
	group.DELETE("", removeBookFromLibraryByID)
	group.POST("", updateBookByID)
}

// SetupRoutes todo
func SetupRoutes(group *gin.RouterGroup) {
	booksHandler(group.Group("/books"))
	bookHandler(group.Group("/books/:id"))
}
