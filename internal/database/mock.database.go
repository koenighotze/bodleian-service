package database

import "log"

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

// NewMockDatabase todo
func NewMockDatabase() Database {
	return mockDatabase{}
}
