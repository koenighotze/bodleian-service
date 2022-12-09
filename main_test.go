package main

import (
	"github.com/golang/mock/gomock"
	"github.com/koenighotze/bodleian-service/internal/mocks"
	"github.com/stretchr/testify/assert"
	"net/http"
	"strings"
	"testing"
)

type StubDb struct {
}

func (db StubDb) Disconnect() error {
	return nil
}

func (db StubDb) SetupDatabase(username string, password string) error {
	return nil
}

func TestMainShouldStartTheDatabase(t *testing.T) {
	ctrl := gomock.NewController(t)
	defer ctrl.Finish()

	mockDb := mock_database.NewMockDatabase(ctrl)
	mockDb.EXPECT().SetupDatabase(gomock.Any(), gomock.Any()).Times(1)
	mockDb.EXPECT().Disconnect().Times(1)

	start(mockDb)
}

func TestMainShouldRegisterServicesUnderAPI(t *testing.T) {
	router := start(&StubDb{})

	found := false
	for _, r := range router.Routes() {
		if strings.HasPrefix(r.Path, "/api/books") {
			found = true
		}
	}
	assert.True(t, found)
}

func TestMainShouldRegisterAHealthEndpoint(t *testing.T) {
	router := start(&StubDb{})

	found := false
	for _, r := range router.Routes() {
		if strings.HasPrefix(r.Path, "/api/health") {
			found = true
		}
	}
	assert.True(t, found)
}

func TestMainShouldRegisterAnEndpointForOptions(t *testing.T) {
	router := start(&StubDb{})

	found := false
	for _, r := range router.Routes() {
		if r.Path == "/" {
			assert.Equal(t, r.Method, http.MethodOptions)
			found = true
		}
	}
	assert.True(t, found)
}
