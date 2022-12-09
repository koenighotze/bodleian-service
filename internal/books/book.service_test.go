package books

import (
	"bytes"
	"encoding/json"
	"fmt"
	"github.com/gin-gonic/gin"
	"github.com/go-http-utils/headers"
	"github.com/google/uuid"
	"github.com/stretchr/testify/assert"
	"net/http"
	"net/http/httptest"
	"strings"
	"testing"
)

var repo = New(nil)

func setupRouter() *gin.Engine {
	r := gin.Default()

	SetupRoutes(r.Group("/api"), repo)

	return r
}

func TestGettingTheBooks(t *testing.T) {
	router := setupRouter()
	w := httptest.NewRecorder()

	req, _ := http.NewRequest(http.MethodGet, "/api/books", nil)
	router.ServeHTTP(w, req)

	assert.Equal(t, http.StatusOK, w.Code)
	var books []Book
	_ = json.Unmarshal(w.Body.Bytes(), &books)
	assert.NotEmpty(t, books)
}

func TestAddingABook(t *testing.T) {
	router := setupRouter()
	w := httptest.NewRecorder()

	book := NewBook(uuid.NewString(), uuid.NewString(), uuid.NewString())
	payload, _ := json.Marshal(book)

	req, _ := http.NewRequest(http.MethodPost, "/api/books", bytes.NewReader(payload))
	router.ServeHTTP(w, req)

	assert.Equal(t, http.StatusCreated, w.Code)
	location := w.Result().Header.Get(headers.Location)
	assert.NotEmpty(t, location)

	w = httptest.NewRecorder()
	req, _ = http.NewRequest(http.MethodGet, location, nil)
	router.ServeHTTP(w, req)

	var bookFromApi Book
	_ = json.Unmarshal(w.Body.Bytes(), &bookFromApi)

	assert.Equal(t, http.StatusOK, w.Code)
	assert.Equal(t, book.Title, bookFromApi.Title)
	assert.Equal(t, book.ISBN, bookFromApi.ISBN)
	assert.Equal(t, book.Authors, bookFromApi.Authors)
	assert.NotEqual(t, book.ID, bookFromApi.ID)
}

func TestUpdatingTheAuthorOfABook(t *testing.T) {
	books, _ := repo.GetAllBooks()
	firstBook := books[0]
	router := setupRouter()
	w := httptest.NewRecorder()
	payload := "{  \"authors\": [\"New Author\"],  \"title\": \"New Title\"}"

	req, _ := http.NewRequest(http.MethodPost, fmt.Sprintf("/api/books/%v", firstBook.ID), strings.NewReader(payload))
	router.ServeHTTP(w, req)
	bookThatShouldBeUpdated, _ := repo.GetBookByID(firstBook.ID)

	assert.Equal(t, http.StatusOK, w.Code)
	assert.Equal(t, "New Title", bookThatShouldBeUpdated.Title)
	assert.Equal(t, firstBook.ISBN, bookThatShouldBeUpdated.ISBN)
	assert.Equal(t, []string{"New Author"}, bookThatShouldBeUpdated.Authors)
}

func TestGetBookByID(t *testing.T) {
	books, _ := repo.GetAllBooks()
	firstBook := books[0]

	router := setupRouter()
	w := httptest.NewRecorder()

	req, _ := http.NewRequest(http.MethodGet, fmt.Sprintf("/api/books/%v", firstBook.ID), nil)
	router.ServeHTTP(w, req)
	var book Book
	_ = json.Unmarshal(w.Body.Bytes(), &book)

	assert.Equal(t, http.StatusOK, w.Code)
	assert.NotEmpty(t, book)
	assert.Equal(t, book, firstBook)
}

func TestRemoveBookByID(t *testing.T) {
	books, _ := repo.GetAllBooks()
	firstBook := books[0]

	router := setupRouter()
	w := httptest.NewRecorder()

	req, _ := http.NewRequest(http.MethodDelete, fmt.Sprintf("/api/books/%v", firstBook.ID), nil)
	router.ServeHTTP(w, req)

	assert.Equal(t, http.StatusOK, w.Code)
	_, err := repo.GetBookByID(firstBook.ID)
	assert.Error(t, err)
}
