package config

import (
	"log"

	"github.com/kelseyhightower/envconfig"
)

type Configuration struct {
	Environment string `default:"DEVELOPMENT"`
	Server      struct {
		Port string `default:"8080"`
	}
	Database struct {
		Username string `default:"pgadmin"`
		Password string `default:"pgadmin"`
	}
}

type DBCredentials struct {
	Username string
	Password string
}

type ConfigurationService interface {
	GetConfiguration() *Configuration
	GetDBCredentials() DBCredentials
	IsProductionEnvironment() bool
}

type configurationService struct {
	configuration *Configuration
}

func (service *configurationService) GetConfiguration() *Configuration {
	return service.configuration
}

func (service *configurationService) GetDBCredentials() DBCredentials {
	return DBCredentials{
		Username: service.configuration.Database.Username,
		Password: service.configuration.Database.Password,
	}
}

func (service *configurationService) IsProductionEnvironment() bool {
	return service.configuration.Environment == "PRODUCTION"
}

func (service *configurationService) logInfo() {
	log.Printf("Configuration is on environment: %v", service.configuration.Environment)
}

func NewConfigurationService() (ConfigurationService, error) {
	var configuration Configuration
	err := envconfig.Process("bodleian", &configuration)
	if err != nil {
		return nil, err
	}

	service := &configurationService{
		configuration: &configuration,
	}
	service.logInfo()

	return service, nil
}
