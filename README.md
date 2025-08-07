# Showcase

This project is aim to reorganize what i have learned, and along the way discover some
platform/framework that I have been utilizing (but built by other member) as a java learning
material and knowledge base.

<!-- TOC -->
* [Showcase](#showcase)
  * [Pre-requisite](#pre-requisite)
  * [TODO](#todo-)
* [How to](#how-to-)
  * [Local Development](#local-development)
  * [Local cluster testing (TODO Update)](#local-cluster-testing-todo-update)
  * [Build image](#build-image)
  * [Deploy image to local cluster](#deploy-image-to-local-cluster)
  * [Get Access token for development](#get-access-token-for-development)
    * [FE flow](#fe-flow)
    * [BE flow](#be-flow)
* [Infrastructure Remark](#infrastructure-remark)
  * [Cluster](#cluster)
  * [Docker](#docker)
    * [Build image](#build-image-1)
  * [DB Remark](#db-remark)
    * [Start up scrit](#start-up-scrit)
    * [Version](#version)
  * [Keycloak](#keycloak)
    * [Setup](#setup)
<!-- TOC -->

## Pre-requisite
- WSL or UNIX env
- k3d installed in path
- kubectl installed in path
- docker installed in path
    - disabled containerd, as of k3d v5.8.3, it doesn't work with containerd image
- added the followings to /etc/hosts file (win: C:\Windows\System32\drivers\etc)
  - `127.0.0.1 keycloak.local`
  - `127.0.0.1 user-profile.local`
- installed JDK 24 in path
- mvn installed in path 
- helm https://helm.sh/docs/intro/install/

## TODO 
> NEXT: Migrate to helm, config health endpoint by pass (no auth). and it should visible to k8s cluster only

Same copy might available in Notion, keep a copy here for future (long) references.
- [ ]  DevOps
  - [x]  Consider re-setup in WSL (window) ?
  - [ ]  try k3d
    - [x]  write dockerFile for app
    - [x]  write deployment.yaml
    - [x]  deploy app to k3d
    - [ ]  config map refactoring
    - [ ]  CICD
      - [ ]  dockerfile → github action → docker hub → helm install?/local deployment
  - [ ]  Vault/ConfigMap/Consul Server
  - [X]  Ingress, Ingress controller
    - [X]  how kso did it? → traefik
  - [ ]  OIDC and jwt token generation
    - [X]  spring oauth 2 client
    - [ ]  Keycloak
      - [x]  install
      - [x]  config app for it
        - [x]  maybe first using a dumb user first
        - [ ]  then only go for integration?
          - [ ]  integrate with user profile service, to mgmt user via user-profile service
            - [x]  create user
            - [ ]  get user and popogate to jwt token as claims
              - [ ]  https://claude.ai/chat/dcc58b7f-b740-4546-a4dc-83ef596bf06d
          - [ ]  https://claude.ai/chat/dcc58b7f-b740-4546-a4dc-83ef596bf06d
        - [ ]  scope/role to jwt
        - [ ]  then only protect user profile service (hiding users/* endpoints or admin endpoint or the whole repo from ingress)
          - [ ]  https://claude.ai/chat/073fc4a2-20d6-4233-bfa9-7613cf800672
      - [X]  Arch that login via keycloak
        - [X]  https://claude.ai/share/36f3e97a-e02e-498a-8059-2c5289f0248a
      - [ ]  Arch that login via a proxy of keycloak (internal login page)
  - [ ]  setup gateway/ spring gateway?
    - [ ]  Proxy, reverse proxy, rate limiting
  - [ ]  Migrate to Helm?
  - [ ]  Istio ?
  - [ ] Start up Script
    - [ ] include NodeJS & Express installation in setup script
    - [ ] include FE startup script in startup script
- [ ]  Ms
  - [x]  Archetype
  - [ ]  User ms→ to gen JWT
  - [ ]  Transfer ms → main flow
  - [ ]  Account ms → to test grpc call between Transfer and Acc
  - [ ]  lang pack → exp with error code translate to a certain message.
  - [ ]  Webclient
  - [ ]  gRPC
  - [ ]  Spring gateway
  - [ ]  Kafka or mq integration
    - [ ]  account balance?
  - [ ]  mambu
  - [ ]  Java OOM Debug/ IntelliJ Profiler
  - [ ]  swagger and swagger ui
    - [ ]  swagger with bearer token in ui
  - [ ]  websocket???
- [ ]  DNS for docker services? (ingress) ?
- [ ]  log
  - [ ]  grafana, fluentD, elastic search?
  - [ ]  service mesh - Trae

# How to 

## Local Development
- Start docker compose for db stack
- start app at port 8080
- test api via 8080

## Local cluster testing (TODO Update)
- Start cluster (run `./devops/start-cluster.bash`)
- Run Helm (?)
  - cd to helm directory
  - helm install user-profile ./base-service-chart -f values/user-profile.yaml
  - to revert  `helm uninstall user-profile`
  - to update ` helm upgrade user-profile ./base-service-chart -f values/user-profile.yaml`

## Build image
- To build image, it is required to:
  - maven build
  - docker build
  - import to k3d registry 
- Referred `build-app.bash` for more info

## Deploy image to local cluster
- Make sure local cluster has been set up (referred `init-cluster.bash`)
- Make sure cluster is started(run `./devops/start-cluster.bash`)
- For Keycloak (TODO: migrate to helm)
  - docker pull quay.io/keycloak/keycloak:26.2.5
  - k3d image import quay.io/keycloak/keycloak:26.2.5 -c showcase-cluster
  - kubectl apply -f keycloak-deployment.yaml
  - kubectl apply -f ingress.yaml
- For app, run helm install to deploy, helm upgrade to add new release, helm uninstall to delete deployment
- referred `helm-install-app.bash` for more info.

## Get Access token for development
### FE flow
- Make sure you have nodejs installed
- Install express, `npm install express --save`
- node ./ui/server.js
- it will start ui page in localhost:3000

### BE flow
- Naviagate to
  `http://keycloak.local:8081/realms/showcase/protocol/openid-connect/auth?client_id=web-client&redirect_uri=http://localhost:3000/callback&response_type=code&scope=openid`
    - since dont have FE code yet, so just copy `code` from response
- Fire /token api to access token
    - ```curl
  curl --location 'http://keycloak.local:8081/realms/showcase/protocol/openid-connect/token' \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --data-urlencode 'grant_type=authorization_code' \
  --data-urlencode 'client_id=web-client' \
  --data-urlencode 'client_secret=dpine2z9X29JN0M92iJM4rzbdR6upYHs' \
  --data-urlencode '
  code=9dcadfb3-3395-4e24-81f1-8ddbe0aada0c.dd1e2cdf-32b5-4491-8d9d-953eb3ead53f.8b5e7b12-2262-45c1-ad1b-5be07192874f' \
  --data-urlencode 'redirect_uri=http://localhost:3000/callback'
    ```
- get access token from response

--
# Infrastructure Remark

## Cluster
- This app is utilizing k3d cluster.
- `k3d cluster create showcase-cluster --registry-create showcase-cluster-registry:0.0.0.0:5000 -p "8081:80@loadbalancer"` create a local cluster in k3d
  - it creates registry in port 5000
  - it creates a port mapping from 8081 (host port) to port 80 (traefik, ingress controller port)

## Docker

### Build image

- referred https://www.baeldung.com/spring-boot-docker-images, which create layer in jar file. In
  docker file extract it
  different layer for caching.
- run `docker build -t showcase/user-profile-service:v1.0 .` to build image.
- run
  `docker run -p 8080:8080 --name user-profile-service-test -t showcase/user-profile-service:v1.0 `
  to test run image in docker.
- run `k3d image import showcase/user-profile-service:v1.1 -c showcase-cluster` to import from
  docker to local registry (k3d)

## DB Remark

### Start up scrit

Although we have `volumes: - ./init:/docker-entrypoint-initdb.d` to let docker run init (create DB)
script,
it would only run once when creating docker containers.

I.e. every time a new DB need to be created (by adding init script, the whole container has to be
destroyed and to be
recreated again to make it happen)

### Version

Using Mysql 8.4.X because Flyway is not supporting MySQL 9.X at time of writing.

--- 

## Keycloak

### Setup

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