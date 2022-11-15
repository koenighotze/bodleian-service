#!/usr/bin/env bash
# when a command fails, bash exits instead of continuing with the rest of the script
set -o errexit
# make the script fail, when accessing an unset variable
set -o nounset
# pipeline command is treated as failed, even if one command in the pipeline fails
set -o pipefail
# enable debug mode, by running your script as TRACE=1
if [[ "${TRACE-0}" == "1" ]]; then set -o xtrace; fi

: "${GITHUB_SHA?'Expected env var GITHUB_SHA not set'}"
: "${GITHUB_REF?'Expected env var GITHUB_REF not set'}"
: "${CONTAINER_REGISTRY?'Expected env var CONTAINER_REGISTRY not set'}"
: "${CONTAINER_PORTS:=8080}"
: "${OUTPUT_MODE:=load}"

IMAGE_NAME="$CONTAINER_REGISTRY/$GITHUB_REPOSITORY:$GITHUB_SHA"

echo "Building image $IMAGE_NAME"
gcloud auth configure-docker "$(echo "$CONTAINER_REGISTRY" | cut -d/ -f1)"
NOW=$(date -u +%Y-%m-%dT%T%z)

if [[ "$GITHUB_REF" = refs/tags/* ]]; then
    GIT_TAG=${GITHUB_REF/refs\/tags\/}
    echo "Building for tag $GIT_TAG"

    echo "git-tag=$GIT_TAG" >> "$GITHUB_ENV"
fi

DOCKER_BUILD_OPTIONS=""
if [[ -n "${GIT_TAG:=}" ]]; then
    DOCKER_BUILD_OPTIONS="--tag=$CONTAINER_REGISTRY/$GITHUB_REPOSITORY:$GIT_TAG"
fi

# shellcheck disable=SC2086
docker buildx build --${OUTPUT_MODE} \
  --tag "$IMAGE_NAME" $DOCKER_BUILD_OPTIONS \
  --label "org.opencontainers.image.revision=${GITHUB_SHA}" \
  --label "org.opencontainers.image.created=${NOW}" \
  .

if [[ "$GITHUB_REF" = refs/tags/* ]]; then
  # shellcheck disable=SC2086
  echo "Tagged image name is $CONTAINER_REGISTRY/$GITHUB_REPOSITORY:$GIT_TAG"
  echo "image-name=$CONTAINER_REGISTRY/$GITHUB_REPOSITORY:$GIT_TAG" >> "$GITHUB_ENV"
fi
echo "raw-image-name=$IMAGE_NAME" >> "$GITHUB_ENV"

echo "::endgroup::"