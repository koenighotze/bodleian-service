package internal

import (
	"log"

	"github.com/koenighotze/bodleian-service/internal/books"
	"github.com/koenighotze/bodleian-service/internal/config"
	"github.com/koenighotze/bodleian-service/internal/database"
	"github.com/koenighotze/bodleian-service/internal/health"
)

type Components struct {
	ConfigurationService config.ConfigurationService
	BookRepository       books.BookRepository
	BookService          books.BookService
	HealthService        health.HealthService
}

func WireComponents() (*Components, error) {
	configurationService, err := config.NewConfigurationService()
	if err != nil {
		log.Fatalf("Cannot handle configuration. Must bail. %v", err)
		return nil, err
	}
	db, err := database.ProvideDatabase(configurationService)
	if err != nil {
		log.Fatalf("Cannot connect to database. Must bail. %v", err)
		return nil, err
	}

	bookRepository := books.NewBookRepository(configurationService, db)
	bookService := books.NewBookService(bookRepository)

	healthService := health.NewHealthService()

	return &Components{
		ConfigurationService: configurationService,
		BookRepository:       bookRepository,
		BookService:          bookService,
		HealthService:        healthService,
	}, nil
}
