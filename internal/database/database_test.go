package database

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestSetupDatabaseReturnNilOnSuccess(t *testing.T) {
	db := MockDatabase{}
	err := db.SetupDatabase("", "")

	assert.Nil(t, err)
}

func TestDisconnectReturnNilOnSuccess(t *testing.T) {
	db := MockDatabase{}

	err := db.Disconnect()

	assert.Nil(t, err)
}
