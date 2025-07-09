# Showcase Project
## Pre-requisite
- Java 24
- mvn
- docker
- k3d


## Getting start
- `k3d cluster create showcase-cluster --registry-create showcase-cluster-registry:0.0.0.0:5000` create a local cluster in k3d
- `k3d registry create myregistry.localhost --port 5000` to create local registrt in k3d
- cd to devops/db, `docker-compose up` to bootstrap db
- navigate to individual app folder (TODO: change to pipeline)
- check where is your current cluster, `kubectl config get-clusters`

## Build and deploy image
- navigate to individual app folder (TODO: change to pipeline)
- run `docker build -t showcase/user-profile-service:v1.0 .` to build image.
- `k3d image import showcase/user-profile-service:v1.1 -c showcase-cluster` to import image to regustrt
- kubectl apply -f .\deployment.yaml