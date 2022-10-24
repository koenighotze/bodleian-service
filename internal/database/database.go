package database

import "log"

func SetupDatabase(username string, _ string) error {
	log.Printf("Setting up the database for %v", username)

	return nil
}

func Disconnect() {
	log.Println("Disconnecting")
}
