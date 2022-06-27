#!/usr/bin/env bash

set -Eeuo pipefail

: "${GITHUB_SHA?'Expected env var GITHUB_SHA not set'}"
: "${GITHUB_REF?'Expected env var GITHUB_REF not set'}"
: "${CONTAINER_REGISTRY?'Expected env var CONTAINER_REGISTRY not set'}"
: "${CONTAINER_PORTS:=8080}"

IMAGE_NAME="$CONTAINER_REGISTRY/$GITHUB_REPOSITORY:$GITHUB_SHA"
gcloud auth configure-docker "$(echo $CONTAINER_REGISTRY | cut -d/ -f1)"
NOW=$(date -u +%Y-%m-%dT%T%z)

if [[ "$GITHUB_REF" = refs/tags/* ]]; then
    GIT_TAG=${GITHUB_REF/refs\/tags\/}
    echo "::set-output name=git-tag::$GIT_TAG"
fi

DOCKER_BUILD_OPTIONS=""
if [[ -n "${GIT_TAG:=}" ]]; then
    DOCKER_BUILD_OPTIONS="--tag=$GIT_TAG"
fi

echo docker build --tag "$IMAGE_NAME" \
  --label "org.opencontainers.image.revision=${GITHUB_SHA}" \
  --label "org.opencontainers.image.created=${NOW}" \
  # shellcheck disable=SC2086
  $DOCKER_BUILD_OPTIONS \
  .

docker build --tag "$IMAGE_NAME" \
  # --label "org.opencontainers.image.revision=${GITHUB_SHA}" \
  # --label "org.opencontainers.image.created=${NOW}" \
  # # shellcheck disable=SC2086
  # $DOCKER_BUILD_OPTIONS \
  .

if [[ "$GITHUB_REF" = refs/tags/* ]]; then
  # shellcheck disable=SC2086
  echo "::set-output name=image-name::$CONTAINER_REGISTRY/$GITHUB_REPOSITORY:$GIT_TAG"
else
  echo "::set-output name=image-name::$IMAGE_NAME"
fi

echo "::endgroup::"