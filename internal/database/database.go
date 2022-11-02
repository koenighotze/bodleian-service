package database

import "log"

// SetupDatabase todo
func SetupDatabase(username string, _ string) error {
	log.Printf("Setting up the database for %v", username)

	return nil
}

// Disconnect todo
func Disconnect() {
	log.Println("Disconnecting")
}
