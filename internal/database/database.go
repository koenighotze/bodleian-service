package database

import "log"

type Database interface {
	Disconnect() error
	SetupDatabase(username string, password string) error
}

type MockDatabase struct {
}

// SetupDatabase todo
func (db MockDatabase) SetupDatabase(username string, _ string) error {
	log.Printf("Setting up the database for %v", username)

	return nil
}

// Disconnect todo
func (db MockDatabase) Disconnect() error {
	log.Println("Disconnecting")

	return nil
}

func New() Database {
	return MockDatabase{}
}
