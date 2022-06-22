package main

import (
	"log"
	"net/http"
)

type fooHandler struct {
	Message string
}

func (f *fooHandler) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	w.Write([]byte(f.Message))
}

func main() {
	http.Handle("/foo", &fooHandler{Message: "hello World"})
	bar := func(w http.ResponseWriter, r *http.Request) {
		w.Write([]byte("bar"))
	}
	http.HandleFunc("/bar", bar)

	err := http.ListenAndServe(":8080", nil)
	if err != nil {
		log.Fatal(err)
	}
}
