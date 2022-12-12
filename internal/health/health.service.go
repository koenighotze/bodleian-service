// Package health
package health

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

type healthService struct {
}

func (service healthService) getHealth(context *gin.Context) {
	context.IndentedJSON(http.StatusOK, gin.H{
		"health": "ok",
	})
}

func healthHandler(group *gin.RouterGroup, service healthService) {
	group.GET("", service.getHealth)
}

// SetupRoutes todo
func SetupRoutes(group *gin.RouterGroup) {
	service := healthService{}

	healthHandler(group.Group("/health"), service)
}
