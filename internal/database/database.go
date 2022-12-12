package database

// Database todo
type Database interface {
	Disconnect() error
	SetupDatabase(username string, password string) error
}
