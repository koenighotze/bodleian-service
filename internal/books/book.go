package books

import (
	"encoding/json"
	"github.com/google/uuid"
	"log"
)

type BookId string

const (
	NoneId = BookId("undefined")
)

type Book struct {
	Id     BookId `json:"id"`
	ISBN   string `json:"isbn"`
	Title  string `json:"title"`
	Author string `json:"author"`
}

func (book Book) ToJSON() []byte {
	asJson, err := json.MarshalIndent(book, "", "   ")
	if err != nil {
		log.Default().Fatalf("Cannot marshal %v", book)
	}
	return asJson
}

func NewBook(isbn string, title string, author string) Book {
	return Book{
		Id:     BookId(uuid.NewString()),
		ISBN:   isbn,
		Title:  title,
		Author: author,
	}
}
