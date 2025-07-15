#!/bin/bash
set -e

cd "$(dirname "$0")"

echo "spin up DB and other localstack"
docker-compose -f db/docker-compose.yml up -d

echo "start k3d cluster"
k3d cluster start showcase-cluster

