package main

import (
	"github.com/gin-gonic/gin"
	"github.com/koenighotze/bodleian-service/internal/books"
	"github.com/koenighotze/bodleian-service/internal/database"
	"github.com/koenighotze/bodleian-service/internal/health"
	"log"
)

func main() {
	log.Default().Println("Starting up...")
	router := gin.Default()

	err := database.SetupDatabase("foo", "bar")
	if err != nil {
		log.Fatalf("Cannot setup connection to database. Must bail. %v", err)
	}
	defer func() {
		if err := database.Disconnect(); err != nil {
			log.Printf("Cannot disconnect because of: %v", err)
		}
	}()

	router.OPTIONS("/")
	api := router.Group("/api")
	books.SetupRoutes(api)
	health.SetupRoutes(api)

	err = router.Run()
	if err != nil {
		log.Fatalf("Cannot start web framework. Must bail. %v", err)
	}
}
