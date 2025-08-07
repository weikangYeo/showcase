#!/bin/bash
set -e

cd "$(dirname "$0")"
cd "cluster/helm"

SERVICES=(
  "user-profile"
)

for service in "${SERVICES[@]}"; do
  helm upgrade "$service" ./base-service-chart -f values/"$service".yaml
done

