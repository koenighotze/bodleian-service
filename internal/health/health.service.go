package health

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

type HealthService struct {
}

func (service HealthService) getHealth(context *gin.Context) {
	context.IndentedJSON(http.StatusOK, gin.H{
		"health": "ok",
	})
}

func healthHandler(group *gin.RouterGroup, service HealthService) {
	group.GET("", service.getHealth)
}

// SetupRoutes todo
func SetupRoutes(group *gin.RouterGroup) {
	service := HealthService{}

	healthHandler(group.Group("/health"), service)
}
