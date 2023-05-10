package health

import (
	"encoding/json"
	"net/http"
	"net/http/httptest"
	"testing"

	"github.com/gin-gonic/gin"
	"github.com/stretchr/testify/assert"
)

func setupRouter() *gin.Engine {
	r := gin.Default()

	SetupRoutes(r.Group("/api"), NewHealthService())

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
