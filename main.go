package main

import (
	"github.com/gin-gonic/gin"
	"github.com/koenighotze/bodleian-service/internal/books"
	"github.com/koenighotze/bodleian-service/internal/database"
	"log"
)

func main() {
	log.Default().Println("Starting up...")
	router := gin.Default()

	err := database.SetupDatabase("foo", "bar")
	if err != nil {
		log.Fatalf("Cannot setup connection to database. Must bail. %v", err)
	}
	defer database.Disconnect()

	router.OPTIONS("/")
	api := router.Group("/api")
	books.SetupRoutes(api)

	err = router.Run()
	if err != nil {
		log.Fatalf("Cannot start web framework. Must bail. %v", err)
	}
}
