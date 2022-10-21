# syntax=docker/dockerfile:1

FROM golang:1.18-buster AS build

ENV USER=bodleian
ENV _UID=10001
RUN addgroup --gid "10001" "bodleian"
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "$(pwd)" \
    --ingroup "${USER}" \
    --no-create-home \
    --uid "${_UID}" \
    "${USER}"

WORKDIR /app

COPY go.mod ./
COPY go.sum ./
RUN go mod download

COPY cmd ./cmd
COPY configs ./configs

# See https://gist.github.com/asukakenji/f15ba7e588ac42795f421b48b8aede63
# See https://github.com/docker/labs/issues/215
RUN CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build -o /bodleian-service main.go

FROM scratch
COPY --from=build /etc/passwd /etc/passwd
COPY --from=build /etc/group /etc/group

WORKDIR /

COPY --from=build /bodleian-service /usr/local/bin/bodleian-service

EXPOSE 8080
ENV USER=bodleian
USER "${USER}":"${USER}"

ENTRYPOINT ["/usr/local/bin/bodleian-service"]
