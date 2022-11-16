package books

import (
	"encoding/json"
	"fmt"
	"github.com/gin-gonic/gin"
	"github.com/stretchr/testify/assert"
	"net/http"
	"net/http/httptest"
	"strings"
	"testing"
)

func setupRouter() *gin.Engine {
	r := gin.Default()

	SetupRoutes(r.Group("/api"))

	return r
}

func TestGettingTheBooks(t *testing.T) {
	router := setupRouter()
	w := httptest.NewRecorder()

	req, _ := http.NewRequest("GET", "/api/books", nil)
	router.ServeHTTP(w, req)

	assert.Equal(t, http.StatusOK, w.Code)
	var books []Book
	_ = json.Unmarshal(w.Body.Bytes(), &books)
	assert.NotEmpty(t, books)
}

func TestUpdatingTheAuthorOfABook(t *testing.T) {
	books, _ := GetAllBooks()
	firstBook := books[0]
	router := setupRouter()
	w := httptest.NewRecorder()
	payload := "{  \"authors\": [\"New Author\"],  \"title\": \"New Title\"}"

	req, _ := http.NewRequest(http.MethodPost, fmt.Sprintf("/api/books/%v", firstBook.ID), strings.NewReader(payload))
	router.ServeHTTP(w, req)
	bookThatShouldBeUpdated, _ := GetBookByID(firstBook.ID)

	assert.Equal(t, http.StatusOK, w.Code)
	assert.Equal(t, "New Title", bookThatShouldBeUpdated.Title)
	assert.Equal(t, firstBook.ISBN, bookThatShouldBeUpdated.ISBN)
	assert.Equal(t, []string{"New Author"}, bookThatShouldBeUpdated.Authors)
}
