package database

import (
	"github.com/jmoiron/sqlx"
	"github.com/koenighotze/bodleian-service/internal/config"
)

func ProvideDatabase(configurationService config.ConfigurationService) (*sqlx.DB, error) {
	dbCredentials := configurationService.GetDBCredentials()

	if configurationService.IsProductionEnvironment() {
		return SetupPostgreSQLDatabase(dbCredentials)
	} else {
		return SetupMockDatabase(dbCredentials)
	}
}
