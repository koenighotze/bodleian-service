package health

import (
	"encoding/json"
	"github.com/gin-gonic/gin"
	"github.com/stretchr/testify/assert"
	"net/http"
	"net/http/httptest"
	"testing"
)

func setupRouter() *gin.Engine {
	r := gin.Default()

	SetupRoutes(r.Group("/api"))

	return r
}

func TestGettingHealth(t *testing.T) {
	router := setupRouter()
	w := httptest.NewRecorder()

	req, _ := http.NewRequest(http.MethodGet, "/api/health", nil)
	router.ServeHTTP(w, req)
	var response map[string]string
	_ = json.Unmarshal(w.Body.Bytes(), &response)

	assert.Equal(t, http.StatusOK, w.Code)
	assert.NotEmpty(t, response)
	assert.Equal(t, response["health"], "ok")
}
