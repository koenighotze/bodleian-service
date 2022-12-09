package database

import "log"

// Database todo
type Database interface {
	Disconnect() error
	SetupDatabase(username string, password string) error
}

type mockDatabase struct {
}

// SetupDatabase todo
func (db mockDatabase) SetupDatabase(username string, _ string) error {
	log.Printf("Setting up the database for %v", username)

	return nil
}

// Disconnect todo
func (db mockDatabase) Disconnect() error {
	log.Println("Disconnecting")

	return nil
}

// New todo
func New() Database {
	return mockDatabase{}
}
