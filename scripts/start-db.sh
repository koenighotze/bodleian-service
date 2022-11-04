#!/usr/bin/env bash
# when a command fails, bash exits instead of continuing with the rest of the script
set -o errexit
# make the script fail, when accessing an unset variable
set -o nounset
# pipeline command is treated as failed, even if one command in the pipeline fails
set -o pipefail
# enable debug mode, by running your script as TRACE=1
if [[ "${TRACE-0}" == "1" ]]; then set -o xtrace; fi

docker run --rm -ti -e POSTGRES_PASSWORD=secret -e POSTGRES_USER=bodleian -p 5432:5432 -v "$PWD/pgdata:/var/lib/postgresql/data" -e PGDATA=/var/lib/postgresql/data/pgdata postgres:14.4-alpine