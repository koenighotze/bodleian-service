package books

import (
	"fmt"
)

var mockHash = make(map[BookID]Book)

func init() {
	var mockData = []Book{
		NewBook("3596294312", "Buddenbrooks. Verfall einer Familie", "Thomas Mann"),
		NewBook("310048391X", "Joseph und seine Brüder", "Thomas Mann"),
		NewBook("3423214120", "Der kleine Hobbit", "J.R.R. Tolkien"),
	}
	for _, book := range mockData {
		mockHash[book.ID] = book
	}
}

// GetBookByID todo
func GetBookByID(bookID BookID) (*Book, error) {
	if book, ok := mockHash[bookID]; ok {
		return &book, nil
	}
	return nil, fmt.Errorf("book %s not found", bookID)
}

// GetAllBooks todo
func GetAllBooks() ([]Book, error) {
	allBooks := make([]Book, 0, len(mockHash))

	for _, book := range mockHash {
		allBooks = append(allBooks, book)
	}
	return allBooks, nil
}

// DeleteBookByID todo
func DeleteBookByID(bookID BookID) error {
	delete(mockHash, bookID)
	return nil
}

// AddNewBook todo
func AddNewBook(book Book) error {
	if _, ok := mockHash[book.ID]; ok {
		return fmt.Errorf("book %s already exists", book.ID)
	}
	mockHash[book.ID] = book
	return nil
}

// UpdateBookByID todo
func UpdateBookByID(originalBookID BookID, updated Book) error {
	book, err := GetBookByID(originalBookID)
	if err != nil {
		return err
	}

	if updated.ISBN != "" {
		book.ISBN = updated.ISBN
	}
	if updated.Authors != nil {
		book.Authors = updated.Authors
	}
	if updated.Title != "" {
		book.Title = updated.Title
	}
	mockHash[book.ID] = *book

	return nil
}
