// Package health
package health

import (
	"github.com/gin-gonic/gin"
)

func healthHandler(group *gin.RouterGroup, service HealthService) {
	group.GET("", service.GetHealth)
}

// SetupRoutes todo
func SetupRoutes(group *gin.RouterGroup, service HealthService) {
	healthHandler(group.Group("/health"), service)
}
