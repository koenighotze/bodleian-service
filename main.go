package main

import (
	"github.com/koenighotze/bodleian-service/books"
	"github.com/koenighotze/bodleian-service/database"
	"log"
	"net/http"
	"os"
)

func getPort() string {
	if value, ok := os.LookupEnv("PORT"); ok {
		return value
	}
	return "8080"
}

func main() {
	log.Default().Println("Starting up...")
	err := database.SetupDatabase("foo", "bar")
	if err != nil {
		log.Fatalf("Cannot setup connection to database. Must bail. %v", err)
	}
	defer database.Disconnect()
	books.SetupRoutes("/api")

	log.Fatal(http.ListenAndServe(":"+getPort(), nil))
}
