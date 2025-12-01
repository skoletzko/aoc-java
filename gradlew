#!/usr/bin/env bash
set -euo pipefail

if ! command -v docker >/dev/null 2>&1; then
    echo "Docker is required to run this wrapper." >&2
    exit 1
fi

COMPOSE_CMD=${COMPOSE_CMD:-docker compose}
SERVICE_NAME=${AOC_DOCKER_SERVICE:-dev}

declare -a compose_args
if [[ -n "${AOC_DOCKER_COMPOSE_FILE:-}" ]]; then
    compose_args+=("-f" "$AOC_DOCKER_COMPOSE_FILE")
fi

if [[ ${1:-} == "shell" ]]; then
    shift
    exec ${COMPOSE_CMD} "${compose_args[@]}" run --rm "${SERVICE_NAME}" bash "$@"
fi

if [[ $# -eq 0 ]]; then
    echo "Usage: ./gradlew <gradle task args>" >&2
    echo "       ./gradlew shell [command] (drop into the dev container)" >&2
    exit 1
fi

exec ${COMPOSE_CMD} "${compose_args[@]}" run --rm "${SERVICE_NAME}" gradle "$@"
