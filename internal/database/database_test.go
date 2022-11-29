package database

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestSetupDatabaseReturnNilOnSuccess(t *testing.T) {
	err := SetupDatabase("", "")

	assert.Nil(t, err)
}

func TestDisconnectReturnNilOnSuccess(t *testing.T) {
	err := Disconnect()

	assert.Nil(t, err)
}
