# Setup K3D (local k8s in docker)

- referred https://k3d.io/stable/#install-specific-release
- brew install k3d

## Create cluster

- k3d cluster create showcase-cluster

## Check status

- kubectl cluster-info
- kubectl get nodes

# Docker

## Build image
- referred https://www.baeldung.com/spring-boot-docker-images, which create layer in jar file. In docker file extract it
  different layer for caching.
- run `docker build -t showcase/user-profile-service .` to build image.

## show images
- docker image ls