// Package database TODO
package database

import (
	"log"

	"github.com/jmoiron/sqlx"
	"github.com/koenighotze/bodleian-service/internal/config"
	_ "github.com/lib/pq"
)

// SetupDatabase todo
func SetupPostgreSQLDatabase(dbCredentials config.DBCredentials) (*sqlx.DB, error) {
	log.Printf("Setting up the database for %v", dbCredentials.Username)
	db, err := sqlx.Connect("postgres", "user=foo dbname=bar sslmode=disable")

	if err != nil {
		return nil, err
	}

	defer func() {
		if err := db.Close(); err != nil {
			log.Printf("Cannot disconnect because of: %v", err)
		}
	}()

	return db, err
}
