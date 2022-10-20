package books

import (
	"fmt"
	"net/http"
)

func productHandler(w http.ResponseWriter, r *http.Request) {
	switch r.Method {
	case http.MethodGet:
	case http.MethodPost:
	case http.MethodDelete:
	case http.MethodPut:
	default:
		w.WriteHeader(http.StatusMethodNotAllowed)
	}

	w.WriteHeader(http.StatusOK)
}

func productsHandler(w http.ResponseWriter, r *http.Request) {
	switch r.Method {
	case http.MethodGet:
	case http.MethodPost:
	case http.MethodOptions:
	default:
		w.WriteHeader(http.StatusMethodNotAllowed)
	}

	w.WriteHeader(http.StatusOK)
}

func SetupRoutes(basePath string) {
	http.HandleFunc(fmt.Sprintf("%s/%s", basePath, "books"), productsHandler)
	http.HandleFunc(fmt.Sprintf("%s/%s", basePath, "books/"), productHandler)
}
