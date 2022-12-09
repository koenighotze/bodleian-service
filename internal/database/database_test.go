package database

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestSetupDatabaseReturnNilOnSuccess(t *testing.T) {
	db := mockDatabase{}
	err := db.SetupDatabase("", "")

	assert.Nil(t, err)
}

func TestDisconnectReturnNilOnSuccess(t *testing.T) {
	db := mockDatabase{}

	err := db.Disconnect()

	assert.Nil(t, err)
}
