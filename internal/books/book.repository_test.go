package books

import (
	"github.com/google/uuid"
	"github.com/stretchr/testify/assert"
	"math/rand"
	"testing"
)

func TestAddNewBookShouldAddABook(t *testing.T) {
	book := NewBook(string(rune(rand.Int())), string(rune(rand.Int())), string(rune(rand.Int())))

	assert.Nil(t, AddNewBook(book))

	foundBook, err := GetBookByID(book.ID)
	assert.Nil(t, err)
	assert.Equal(t, book, *foundBook)
}

func TestAddNewBookShouldReturnAnErrorIfTheBookIsAlreadyKnown(t *testing.T) {
	book := NewBook(string(rune(rand.Int())), string(rune(rand.Int())), string(rune(rand.Int())))
	assert.Nil(t, AddNewBook(book))

	assert.NotNil(t, AddNewBook(book))
}

func TestUpdateBookSetsISBNTitleAndAuthor(t *testing.T) {
	books, _ := GetAllBooks()
	originalBook := books[rand.Intn(cap(books))]

	book := NewBook("A different ISBN", "A different title", "a different author")

	assert.Nil(t, UpdateBookByID(originalBook.ID, book))

	foundBook, err := GetBookByID(originalBook.ID)
	assert.Nil(t, err)
	assert.Equal(t, book.Title, foundBook.Title)
	assert.Equal(t, book.Authors, foundBook.Authors)
	assert.Equal(t, book.ISBN, foundBook.ISBN)
	assert.NotEqual(t, book.ID, foundBook.ID)
}

func TestUpdateBookByIDAnUnknownBookShouldReturnAnError(t *testing.T) {
	err := UpdateBookByID(BookID(uuid.NewString()), NewBook("A different ISBN", "A different title", "a different author"))

	assert.NotNil(t, err)
}

func TestDeleteBookByID(t *testing.T) {
	books, _ := GetAllBooks()
	originalBook := books[rand.Intn(cap(books))]

	assert.Nil(t, DeleteBookByID(originalBook.ID))

	foundBook, err := GetBookByID(originalBook.ID)
	assert.Nil(t, foundBook)
	assert.NotNil(t, err)

}
