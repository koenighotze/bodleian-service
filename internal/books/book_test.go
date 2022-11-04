package books

import (
	"github.com/google/uuid"
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestNewBookReturnsABookWithAnId(t *testing.T) {
	book := NewBook(uuid.NewString(), uuid.NewString(), uuid.NewString())

	assert.NotEmpty(t, book.ID)
}
