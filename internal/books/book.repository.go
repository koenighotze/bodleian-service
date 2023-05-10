package books

import (
	"github.com/jmoiron/sqlx"
	"github.com/koenighotze/bodleian-service/internal/config"
)

// BookRepository todo
type BookRepository interface {
	GetBookByID(bookID BookID) (*Book, error)
	GetAllBooks() ([]Book, error)
	UpdateBookByID(originalBookID BookID, updated Book) error
	AddNewBook(book Book) error
	DeleteBookByID(bookID BookID) error
}

func NewBookRepository(configurationService config.ConfigurationService, db *sqlx.DB) BookRepository {
	if configurationService.IsProductionEnvironment() {
		return NewPostgreSQLBookRepository(db)
	} else {
		return NewInMemoryBookRepository()
	}
}
