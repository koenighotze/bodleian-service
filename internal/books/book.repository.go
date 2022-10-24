package books

import (
	"fmt"
	"log"
)

var mockHash = make(map[BookId]Book)

func init() {
	var mockData = []Book{
		NewBook("3596294312", "Buddenbrooks. Verfall einer Familie", "Thomas Mann"),
		NewBook("310048391X", "Joseph und seine Brüder", "Thomas Mann"),
		NewBook("3423214120", "Der kleine Hobbit", "J.R.R. Tolkien"),
	}
	for _, book := range mockData {
		mockHash[book.Id] = book
	}
}

func GetBookById(bookId BookId) (*Book, error) {
	if book, ok := mockHash[bookId]; ok {
		return &book, nil
	}
	return nil, fmt.Errorf("book %s not found", bookId)
}

func GetAllBooks() ([]Book, error) {
	allBooks := make([]Book, 0, len(mockHash))

	for _, book := range mockHash {
		allBooks = append(allBooks, book)
	}
	return allBooks, nil
}

func DeleteBookById(bookId BookId) error {
	delete(mockHash, bookId)
	return nil
}

func AddNewBook(book Book) error {
	if _, ok := mockHash[book.Id]; ok {
		return fmt.Errorf("book %s already exists", book.Id)
	}
	mockHash[book.Id] = book
	return nil
}

func UpdateBook(original Book, updated Book) error {
	log.Printf("Should update %v with %v", original, updated)
	return nil
}
