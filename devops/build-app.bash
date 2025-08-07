#!/bin/bash
set -e

cd "$(dirname "$0")"

SERVICES=(
  "user-profile-service"
)

for service_dir in "${SERVICES[@]}"; do
  cd "../$service_dir"
  echo "Maven Build"
  mvn clean package

  echo "Docker build"
  docker build -t showcase/"$service_dir":v1.0 .

  echo "Import image to k3d"
  k3d image import showcase/"$service_dir":v1.0 -c showcase-cluster
done

