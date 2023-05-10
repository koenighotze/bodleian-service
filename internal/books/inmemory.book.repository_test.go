package books

import (
	"math/rand"
	"testing"

	"github.com/google/uuid"
	"github.com/stretchr/testify/assert"
)

func TestAddNewBookShouldAddABook(t *testing.T) {
	repo := NewInMemoryBookRepository()
	book := NewBook(string(rune(rand.Int())), string(rune(rand.Int())), string(rune(rand.Int())))

	assert.Nil(t, repo.AddNewBook(book))

	foundBook, err := repo.GetBookByID(book.ID)
	assert.Nil(t, err)
	assert.Equal(t, book, *foundBook)
}

func TestAddNewBookShouldReturnAnErrorIfTheBookIsAlreadyKnown(t *testing.T) {
	repo := NewInMemoryBookRepository()
	book := NewBook(string(rune(rand.Int())), string(rune(rand.Int())), string(rune(rand.Int())))
	assert.Nil(t, repo.AddNewBook(book))

	assert.NotNil(t, repo.AddNewBook(book))
}

func TestUpdateBookSetsISBNTitleAndAuthor(t *testing.T) {
	repo := NewInMemoryBookRepository()
	books, _ := repo.GetAllBooks()
	originalBook := books[rand.Intn(cap(books))]

	book := NewBook("A different ISBN", "A different title", "a different author")

	assert.Nil(t, repo.UpdateBookByID(originalBook.ID, book))

	foundBook, err := repo.GetBookByID(originalBook.ID)
	assert.Nil(t, err)
	assert.Equal(t, book.Title, foundBook.Title)
	assert.Equal(t, book.Authors, foundBook.Authors)
	assert.Equal(t, book.ISBN, foundBook.ISBN)
	assert.NotEqual(t, book.ID, foundBook.ID)
}

func TestUpdateBookByIDAnUnknownBookShouldReturnAnError(t *testing.T) {
	repo := NewInMemoryBookRepository()
	err := repo.UpdateBookByID(BookID(uuid.NewString()), NewBook("A different ISBN", "A different title", "a different author"))

	assert.NotNil(t, err)
}

func TestDeleteBookByID(t *testing.T) {
	repo := NewInMemoryBookRepository()
	books, _ := repo.GetAllBooks()
	originalBook := books[rand.Intn(cap(books))]

	assert.Nil(t, repo.DeleteBookByID(originalBook.ID))

	foundBook, err := repo.GetBookByID(originalBook.ID)
	assert.Nil(t, foundBook)
	assert.NotNil(t, err)

}
