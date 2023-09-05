package main

import (
	"net/http"
	"strings"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestStart_ShouldRegisterServicesUnderAPI(t *testing.T) {
	router, _ := start()

	found := false
	for _, r := range router.Routes() {
		if strings.HasPrefix(r.Path, "/api/books") {
			found = true
		}
	}
	assert.True(t, found)
}

func TestStart_ShouldRegisterAHealthEndpoint(t *testing.T) {
	router, _ := start()

	found := false
	for _, r := range router.Routes() {
		if strings.HasPrefix(r.Path, "/api/health") {
			found = true
		}
	}
	assert.True(t, found)
}

func TestStart_ShouldRegisterAnEndpointForOptions(t *testing.T) {
	router, _ := start()

	found := false
	for _, r := range router.Routes() {
		if r.Path == "/" {
			assert.Equal(t, r.Method, http.MethodOptions)
			found = true
		}
	}
	assert.True(t, found)
}
