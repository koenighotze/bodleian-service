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

if [[ "$GITHUB_REF" = refs/tags/* ]]; then
  GIT_TAG=${GITHUB_REF/refs\/tags\/}
  # shellcheck disable=SC2086
  echo "image-name=$CONTAINER_REGISTRY/$GITHUB_REPOSITORY:$GIT_TAG" >> "$GITHUB_ENV"
else
  echo "image-name=$CONTAINER_REGISTRY/$GITHUB_REPOSITORY:$GITHUB_SHA" >> "$GITHUB_ENV"
fi
