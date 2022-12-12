package books

// BookRepository todo
type BookRepository interface {
	GetBookByID(bookID BookID) (*Book, error)
	GetAllBooks() ([]Book, error)
	UpdateBookByID(originalBookID BookID, updated Book) error
	AddNewBook(book Book) error
	DeleteBookByID(bookID BookID) error
}
