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
## Setup
- after deployed services & ingress, added host file (/etc/hosts), access
  `http://keycloak.local:8081` for admin console
    - user: admin, pwd: admin
- Create realm `showcase`
- to get realm metadata info, access
  `http://${host}:${port}/realms/${realm-name}/.well-known/uma2-configuration`
    - can url like token endpoint, introspect endpoint etc here.
- create a dummy client act as a FE app, and allow valid url "http://localhost:3000"
    - e.g. "web-client" at this case
- go to realm, admin-cli client
  - enable service account authentication (so can use client secret in keycloak admin client)
  - copy client secret and paste in application.yaml (user profile service)
- create a role called `realm-admin` (or equivalent) to manage realm user
  - grant realm-management:manage-user permission to this role
  - grant this role to admin-cli service account
## Login
- Naviagate to `http://keycloak.local:8081/realms/showcase/protocol/openid-connect/auth?client_id=web-client&redirect_uri=http://localhost:3000/callback&response_type=code&scope=openid`
  - since dont have FE code yet, so just copy `code` from response
- Fire /token api to access token
  - ```curl
    curl --location 'http://keycloak.local:8081/realms/showcase/protocol/openid-connect/token' \
    --header 'Content-Type: application/x-www-form-urlencoded' \
    --data-urlencode 'grant_type=authorization_code' \
    --data-urlencode 'client_id=web-client' \
    --data-urlencode 'client_secret=dpine2z9X29JN0M92iJM4rzbdR6upYHs' \
    --data-urlencode 'code=9dcadfb3-3395-4e24-81f1-8ddbe0aada0c.dd1e2cdf-32b5-4491-8d9d-953eb3ead53f.8b5e7b12-2262-45c1-ad1b-5be07192874f' \
    --data-urlencode 'redirect_uri=http://localhost:3000/callback'
    ```
- get access token from response