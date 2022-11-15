#!/usr/bin/env bash
# when a command fails, bash exits instead of continuing with the rest of the script
set -o errexit
# make the script fail, when accessing an unset variable
set -o nounset
# pipeline command is treated as failed, even if one command in the pipeline fails
set -o pipefail
# enable debug mode, by running your script as TRACE=1
if [[ "${TRACE-0}" == "1" ]]; then set -o xtrace; fi

: "${BASE_URL?'Expected env var BASE_URL not set'}"
: "${BEARER_TOKEN?'Expected env var BEARER_TOKEN not set'}"

newman run --env-var HOST="$BASE_URL" --env-var TOKEN="$BEARER_TOKEN" Bodleian.postman_collection.jsong
