package books

import (
	"fmt"
	"github.com/koenighotze/bodleian-service/internal/database"
)

type BookRepository interface {
	GetBookByID(bookID BookID) (*Book, error)
	GetAllBooks() ([]Book, error)
	UpdateBookByID(originalBookID BookID, updated Book) error
	AddNewBook(book Book) error
	DeleteBookByID(bookID BookID) error
}

type InMemoryBookRepository struct {
	mockHash map[BookID]Book
}

func New(_ database.Database) BookRepository {
	var mockHash = make(map[BookID]Book)
	var mockData = []Book{
		NewBook("3596294312", "Buddenbrooks. Verfall einer Familie", "Thomas Mann"),
		NewBook("310048391X", "Joseph und seine Brüder", "Thomas Mann"),
		NewBook("3423214120", "Der kleine Hobbit", "J.R.R. Tolkien"),
	}
	for _, book := range mockData {
		mockHash[book.ID] = book
	}

	return &InMemoryBookRepository{
		mockHash: mockHash,
	}
}

// GetBookByID todo
func (repo *InMemoryBookRepository) GetBookByID(bookID BookID) (*Book, error) {
	if book, ok := repo.mockHash[bookID]; ok {
		return &book, nil
	}
	return nil, fmt.Errorf("book %s not found", bookID)
}

// GetAllBooks todo
func (repo *InMemoryBookRepository) GetAllBooks() ([]Book, error) {
	allBooks := make([]Book, 0, len(repo.mockHash))

	for _, book := range repo.mockHash {
		allBooks = append(allBooks, book)
	}
	return allBooks, nil
}

// DeleteBookByID todo
func (repo *InMemoryBookRepository) DeleteBookByID(bookID BookID) error {
	delete(repo.mockHash, bookID)
	return nil
}

// AddNewBook todo
func (repo *InMemoryBookRepository) AddNewBook(book Book) error {
	if _, ok := repo.mockHash[book.ID]; ok {
		return fmt.Errorf("book %s already exists", book.ID)
	}
	repo.mockHash[book.ID] = book
	return nil
}

// UpdateBookByID todo
func (repo *InMemoryBookRepository) UpdateBookByID(originalBookID BookID, updated Book) error {
	book, err := repo.GetBookByID(originalBookID)
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
	repo.mockHash[book.ID] = *book

	return nil
}
