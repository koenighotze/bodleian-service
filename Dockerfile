# syntax=docker/dockerfile:1

FROM golang:1.16-buster AS build

WORKDIR /app

COPY go.mod ./
COPY go.sum ./
RUN go mod download

COPY *.go ./

RUN go build -o /bodleian-service

FROM scratch

WORKDIR /

COPY --from=build /bodleian-service /bodleian-service

EXPOSE 8080

USER nonroot:nonroot

ENTRYPOINT ["/bodleian-service"]
