// Package database TODO
package database

import (
	"log"

	"github.com/jmoiron/sqlx"
	"github.com/koenighotze/bodleian-service/internal/config"
)

func newMockDB() *sqlx.DB {
	return &sqlx.DB{}
}

func SetupMockDatabase(dbCredentials config.DBCredentials) (*sqlx.DB, error) {
	log.Printf("Setting up the mock database for %v", dbCredentials.Username)

	return newMockDB(), nil
}
