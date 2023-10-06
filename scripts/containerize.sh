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
: "${GITHUB_SERVER_URL?'Expected env var GITHUB_SERVER_URL not set'}"
: "${GITHUB_REPOSITORY?'Expected env var GITHUB_REPOSITORY not set'}"
: "${CONTAINER_REGISTRY?'Expected env var CONTAINER_REGISTRY not set'}"
: "${CONTAINER_PORTS:=8080}"
: "${OUTPUT_MODE:=}"

IMAGE_NAME="$CONTAINER_REGISTRY/$GITHUB_REPOSITORY:$GITHUB_SHA"

echo "Building image $IMAGE_NAME"
gcloud auth configure-docker "$(echo "$CONTAINER_REGISTRY" | cut -d/ -f1)"

if [[ "$GITHUB_REF" = refs/tags/* ]]; then
    GIT_TAG=${GITHUB_REF/refs\/tags\/}
    echo "Building for tag $GIT_TAG"

    echo "git-tag=$(echo "$GIT_TAG" | tr . -)" >> "$GITHUB_ENV"
else
    echo "git-tag=main-latest" >> "$GITHUB_ENV"
fi

DOCKER_BUILD_OPTIONS=""
if [[ -n "${GIT_TAG:=}" ]]; then
    DOCKER_BUILD_OPTIONS="--tag=$CONTAINER_REGISTRY/$GITHUB_REPOSITORY:$GIT_TAG"
fi
echo "$DOCKER_BUILD_OPTIONS"

# # shellcheck disable=SC2086
# docker buildx build --${OUTPUT_MODE} \
#   --tag "$IMAGE_NAME" $DOCKER_BUILD_OPTIONS \
#   --label "org.opencontainers.image.revision=${GITHUB_SHA}" \
#   --label "org.opencontainers.image.created=${NOW}" \
#   .

OCI_REVISION="${GITHUB_SHA}" \
OCI_SOURCE="$(git config --get remote.origin.url)" \
OCI_URL="$GITHUB_SERVER_URL/$GITHUB_REPOSITORY" \
  ./gradlew bootBuildImage --imageName="$IMAGE_NAME" "$OUTPUT_MODE"

if [[ "$GITHUB_REF" = refs/tags/* ]]; then
    # shellcheck disable=SC2086
    echo "Tagged image name is $CONTAINER_REGISTRY/$GITHUB_REPOSITORY:$GIT_TAG"
    echo "image-name=$CONTAINER_REGISTRY/$GITHUB_REPOSITORY:$GIT_TAG" >> "$GITHUB_ENV"
else
    echo "image-name=$IMAGE_NAME" >> "$GITHUB_ENV"
fi

echo "::endgroup::"