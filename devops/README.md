# Setup K3D (local k8s in docker)

- referred https://k3d.io/stable/#install-specific-release
- brew install k3d/ choco install k3d

## Create cluster

- k3d cluster create showcase-cluster

## Check status

- kubectl cluster-info
- kubectl get nodes
- kubectl config current-context
- k3d cluster start showcase-cluster

--- 

# Docker

## Build image

- referred https://www.baeldung.com/spring-boot-docker-images, which create layer in jar file. In
  docker file extract it
  different layer for caching.
- run `docker build -t showcase/user-profile-service:v1.0 .` to build image.
- run
  `docker run -p 8080:8080 --name user-profile-service-test -t showcase/user-profile-service:v1.0 `
  to test run image in docker.
- run `k3d image import showcase/user-profile-service:v1.1 -c showcase-cluster` to import from
  docker to local registry (k3d)

## show images

- docker image ls
  --

# DB Remark

## Start up scrit

Although we have `volumes: - ./init:/docker-entrypoint-initdb.d` to let docker run init (create DB)
script,
it would only run once when creating docker containers.

I.e. every time a new DB need to be created (by adding init script, the whole container has to be
destroyed and to be
recreated again to make it happen)

## Version

Using Mysql 8.4.X because Flyway is not supporting MySQL 9.X at time of writing.

--- 

# Keycloak

after deploy (referred run book), to access admin page:

- kubectl port-forward service/keycloak 8080:8080
  - or (to be tested) via ingress controller
      - ```yaml
        apiVersion: networking.k8s.io/v1
        kind: Ingress
        metadata:
        name: keycloak-ingress
        annotations:
        nginx.ingress.kubernetes.io/rewrite-target: /
        spec:
        rules:
          - host: keycloak.local
            http:
            paths:
              - path: /
                pathType: Prefix
                backend:
                service:
                name: keycloak
                port:
                number: 8080
    ```
  - and Add keycloak.local to your host's /etc/hosts pointing to the k3d cluster's ingress IP
- username password = admin