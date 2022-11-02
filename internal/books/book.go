package books

import (
	"github.com/google/uuid"
)

// BookID The ID of a book
type BookID string

const (
	// NoneID represents a 'null' ID
	NoneID = BookID("undefined")
)

// Book represents a book
type Book struct {
	ID     BookID `json:"id"`
	ISBN   string `json:"isbn"`
	Title  string `json:"title"`
	Author string `json:"author"`
}

// NewBook is a factory method for creating new Books
func NewBook(isbn string, title string, author string) Book {
	return Book{
		ID:     BookID(uuid.NewString()),
		ISBN:   isbn,
		Title:  title,
		Author: author,
	}
}
