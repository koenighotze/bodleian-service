package books

import (
	"encoding/json"
	"fmt"
	"github.com/go-http-utils/headers"
	"github.com/google/uuid"
	"github.com/ldez/mimetype"
	"io"
	"log"
	"net/http"
	"strings"
)

const apiPath = "books"

func getBookIdFromRequest(r *http.Request) (BookId, error) {
	segments := strings.Split(r.URL.Path, apiPath+"/")
	if len(segments) != 2 {
		return NoneId, fmt.Errorf("cannot extract id from %s", r.URL.Path)
	}

	return BookId(segments[1]), nil
}

func getRequestedBook(w http.ResponseWriter, r *http.Request) (*Book, error) {
	id, err := getBookIdFromRequest(r)
	if err != nil {
		log.Println(err)
		w.WriteHeader(http.StatusBadRequest)
		return nil, err
	}
	book, err := GetBookById(id)
	if err != nil {
		log.Println(err)
		w.WriteHeader(http.StatusNotFound)
		return nil, err
	}
	return book, nil
}

func getBookById(w http.ResponseWriter, book *Book) {
	w.Header().Set(headers.ContentType, mimetype.ApplicationJSON)
	w.WriteHeader(http.StatusOK)
	_, err := w.Write(book.ToJSON())
	if err != nil {
		log.Println(err)
	}
}

func updateBook(w http.ResponseWriter, r *http.Request, book *Book) {
	body, err := io.ReadAll(r.Body)
	if err != nil {
		w.WriteHeader(http.StatusBadRequest)
		return
	}
	var newBook = Book{}
	err = json.Unmarshal(body, &newBook)
	if err != nil {
		w.WriteHeader(http.StatusBadRequest)
		return
	}

	err = UpdateBook(*book, newBook)
	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		return
	}

	w.WriteHeader(http.StatusOK)
}

func deleteBook(w http.ResponseWriter, book *Book) {
	err := DeleteBookById(book.Id)
	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		return
	}
	w.WriteHeader(http.StatusOK)
}

func getAllBooks(w http.ResponseWriter, _ *http.Request) {
	books, err := GetAllBooks()
	if err != nil {
		log.Println(err)
		w.WriteHeader(http.StatusInternalServerError)
		return
	}

	asJson, err := json.MarshalIndent(books, "", "   ")
	if err != nil {
		log.Println(err)
		w.WriteHeader(http.StatusInternalServerError)
		return
	}
	w.Header().Set(headers.ContentType, mimetype.ApplicationJSON)
	w.WriteHeader(http.StatusOK)

	_, err = w.Write(asJson)
	if err != nil {
		log.Println(err)
	}
}

func createNewBook(w http.ResponseWriter, r *http.Request) {
	body, err := io.ReadAll(r.Body)
	if err != nil {
		w.WriteHeader(http.StatusBadRequest)
		return
	}
	var newBook = Book{}
	err = json.Unmarshal(body, &newBook)
	if err != nil {
		w.WriteHeader(http.StatusBadRequest)
		return
	}

	newBook.Id = BookId(uuid.NewString())
	err = AddNewBook(newBook)
	if err != nil {
		log.Println(err)
		w.WriteHeader(http.StatusInternalServerError)
		return
	}

	w.Header().Set(headers.Location, fmt.Sprintf("%s/%s", r.URL.Path, newBook.Id))
	w.WriteHeader(http.StatusCreated)
}

func bookHandler(w http.ResponseWriter, r *http.Request) {
	book, err := getRequestedBook(w, r)
	if err != nil {
		return
	}
	switch r.Method {
	case http.MethodGet:
		getBookById(w, book)
	case http.MethodDelete:
		deleteBook(w, book)
	case http.MethodPut:
		updateBook(w, r, book)
	default:
		w.WriteHeader(http.StatusMethodNotAllowed)
	}
}

func booksHandler(w http.ResponseWriter, r *http.Request) {
	switch r.Method {
	case http.MethodGet:
		getAllBooks(w, r)
	case http.MethodPost:
		createNewBook(w, r)
	case http.MethodOptions:
	default:
		w.WriteHeader(http.StatusMethodNotAllowed)
	}
}

func SetupRoutes(basePath string) {
	http.HandleFunc(fmt.Sprintf("%s/%s", basePath, apiPath), booksHandler)
	http.HandleFunc(fmt.Sprintf("%s/%s/", basePath, apiPath), bookHandler)
}
