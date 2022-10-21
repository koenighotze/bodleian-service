package books

import (
	"encoding/json"
	"log"
)

type Book struct {
	Id     string `json:"id"`
	IBAN   string `json:"iban"`
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
