// Package books
package books

import (
	"github.com/gin-gonic/gin"
)

func booksHandler(group *gin.RouterGroup, service BookService) {
	group.GET("", service.getAllBooks)
	group.POST("", service.addNewBookToLibrary)
}

func bookHandler(group *gin.RouterGroup, service BookService) {
	group.GET("", service.getBookByID)
	group.DELETE("", service.removeBookFromLibraryByID)
	group.POST("", service.updateBookByID)
}

// SetupRoutes todo
func SetupRoutes(group *gin.RouterGroup, service BookService) {
	booksHandler(group.Group("/books"), service)
	bookHandler(group.Group("/books/:id"), service)
}
