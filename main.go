package main

import (
	"log"
	"os"

	"github.com/gin-gonic/gin"

	"github.com/koenighotze/bodleian-service/docs"
	"github.com/koenighotze/bodleian-service/internal"
	"github.com/koenighotze/bodleian-service/internal/books"
	"github.com/koenighotze/bodleian-service/internal/health"
	swaggerfiles "github.com/swaggo/files"
	ginSwagger "github.com/swaggo/gin-swagger"
	// swagger embed files
)

// @BasePath /api

func start() (*gin.Engine, error) {
	components, err := internal.WireComponents()
	if err != nil {
		return nil, err
	}

	log.Default().Println("Starting up...")
	router := gin.Default()
	docs.SwaggerInfo.BasePath = "/api/v1"

	router.OPTIONS("/")
	api := router.Group("/api")

	books.SetupRoutes(api, components.BookService)
	health.SetupRoutes(api, components.HealthService)
	router.GET("/swagger/*any", ginSwagger.WrapHandler(swaggerfiles.Handler))

	return router, nil
}

func main() {
	router, err := start()
	if err != nil || router.Run() != nil {
		log.Fatalf("Cannot start web framework. Must bail. %v", err)
		os.Exit(1)
	}
}
