// Package health
package health

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

type HealthService interface {
	GetHealth(context *gin.Context)
}

type healthService struct {
}

func (service healthService) GetHealth(context *gin.Context) {
	context.IndentedJSON(http.StatusOK, gin.H{
		"health": "ok",
	})
}

func NewHealthService() HealthService {
	return healthService{}
}
