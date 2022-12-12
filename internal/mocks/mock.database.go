// Code generated by MockGen. DO NOT EDIT.
// Source: internal/database/database.go

// Package mock_database is a generated GoMock package.
package mock_database

import (
	reflect "reflect"

	gomock "github.com/golang/mock/gomock"
)

// MockDatabase is a mock of Database interface.
type MockDatabase struct {
	ctrl     *gomock.Controller
	recorder *MockDatabaseMockRecorder
}

// MockDatabaseMockRecorder is the mock recorder for MockDatabase.
type MockDatabaseMockRecorder struct {
	mock *MockDatabase
}

// NewMockDatabase creates a new mock instance.
func NewMockDatabase(ctrl *gomock.Controller) *MockDatabase {
	mock := &MockDatabase{ctrl: ctrl}
	mock.recorder = &MockDatabaseMockRecorder{mock}
	return mock
}

// EXPECT returns an object that allows the caller to indicate expected use.
func (m *MockDatabase) EXPECT() *MockDatabaseMockRecorder {
	return m.recorder
}

// Disconnect mocks base method.
func (m *MockDatabase) Disconnect() error {
	m.ctrl.T.Helper()
	ret := m.ctrl.Call(m, "Disconnect")
	ret0, _ := ret[0].(error)
	return ret0
}

// Disconnect indicates an expected call of Disconnect.
func (mr *MockDatabaseMockRecorder) Disconnect() *gomock.Call {
	mr.mock.ctrl.T.Helper()
	return mr.mock.ctrl.RecordCallWithMethodType(mr.mock, "Disconnect", reflect.TypeOf((*MockDatabase)(nil).Disconnect))
}

// SetupDatabase mocks base method.
func (m *MockDatabase) SetupDatabase(username, password string) error {
	m.ctrl.T.Helper()
	ret := m.ctrl.Call(m, "SetupDatabase", username, password)
	ret0, _ := ret[0].(error)
	return ret0
}

// SetupDatabase indicates an expected call of SetupDatabase.
func (mr *MockDatabaseMockRecorder) SetupDatabase(username, password interface{}) *gomock.Call {
	mr.mock.ctrl.T.Helper()
	return mr.mock.ctrl.RecordCallWithMethodType(mr.mock, "SetupDatabase", reflect.TypeOf((*MockDatabase)(nil).SetupDatabase), username, password)
}