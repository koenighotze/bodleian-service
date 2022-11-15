#!/usr/bin/env bash
# when a command fails, bash exits instead of continuing with the rest of the script
set -o errexit
# make the script fail, when accessing an unset variable
set -o nounset
# pipeline command is treated as failed, even if one command in the pipeline fails
set -o pipefail
# enable debug mode, by running your script as TRACE=1
if [[ "${TRACE-0}" == "1" ]]; then set -o xtrace; fi

newman run --env-var HOST=https://backend-83496-btesdmljxq-ey.a.run.app --env-var TOKEN=$(gcloud auth print-identity-token) Bodleian.postman_collection.jsong
