package books

import (
	"fmt"

	"github.com/jmoiron/sqlx"
)

type postgreSQLBookRepository struct {
	db *sqlx.DB
}

func NewPostgreSQLBookRepository(db *sqlx.DB) BookRepository {
	return &postgreSQLBookRepository{
		db: db,
	}
}

// GetBookByID todo
func (repo *postgreSQLBookRepository) GetBookByID(bookID BookID) (*Book, error) {
	return nil, fmt.Errorf("book %s not found", bookID)
}

// GetAllBooks todo
func (repo *postgreSQLBookRepository) GetAllBooks() ([]Book, error) {
	return nil, nil
}

// DeleteBookByID todo
func (repo *postgreSQLBookRepository) DeleteBookByID(bookID BookID) error {
	return nil
}

// AddNewBook todo
func (repo *postgreSQLBookRepository) AddNewBook(book Book) error {
	return nil
}

// UpdateBookByID todo
func (repo *postgreSQLBookRepository) UpdateBookByID(originalBookID BookID, updated Book) error {
	return nil
}
