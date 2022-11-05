package health

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

func getHealth(context *gin.Context) {
	context.IndentedJSON(http.StatusOK, gin.H{
		"health": "ok",
	})
}

func healthHandler(group *gin.RouterGroup) {
	group.GET("", getHealth)
}

// SetupRoutes todo
func SetupRoutes(group *gin.RouterGroup) {
	healthHandler(group.Group("/health"))
}
