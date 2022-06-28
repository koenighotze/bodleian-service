package main

import (
	"testing"
)

func TestHelloName(t *testing.T) {
	name := "Gladys"
	if name == "Foo" {
		t.Fatalf("Strange")
	}

	t.Log("Well done")
}
