#!/bin/bash
set -e

cd "$(dirname "$0")"

echo "spin up DB and other localstack"
docker-compose -f db/docker-compose.yml up -d

echo "creating k3d cluster with registry and port mapping"
k3d cluster create showcase-cluster --registry-create showcase-cluster-registry:0.0.0.0:5000 -p "8081:80@loadbalancer"

echo "pull keycloak image and import to regisry"
docker pull quay.io/keycloak/keycloak:26.2.5
k3d image import quay.io/keycloak/keycloak:26.2.5 -c showcase-cluster

echo "deploy keycloak"
kubectl apply -f ./cluster/keycloak-deployment.yaml
kubectl apply -f ./cluster/ingress.yaml

