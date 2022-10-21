package database

import "log"

func SetupDatabase(username string, password string) error {
	log.Println("Setting up the database")

	return nil
}

func Disconnect() {
	log.Println("Disconnecting")
}
