package books

import (
	"github.com/stretchr/testify/assert"
	"math/rand"
	"testing"
)

func TestAddNewBookShouldAddABook(t *testing.T) {
	book := NewBook(string(rune(rand.Int())), string(rune(rand.Int())), string(rune(rand.Int())))

	assert.Nil(t, AddNewBook(book))

	foundBook, err := GetBookById(book.Id)
	assert.Nil(t, err)
	assert.Equal(t, book, *foundBook)
}

func TestAddNewBookShouldReturnAnErrorIfTheBookIsAlreadyKnown(t *testing.T) {
	book := NewBook(string(rune(rand.Int())), string(rune(rand.Int())), string(rune(rand.Int())))
	assert.Nil(t, AddNewBook(book))

	assert.NotNil(t, AddNewBook(book))
}
