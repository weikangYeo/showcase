# Showcase

This project is aim to reorganize what i have learned, and along the way discover some
platform/framework that I have been utilizing (but built by other member) as a java learning
material and knowledge base.

> Refer RunBook.md for Pre-requisite items.

## TODO 
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

--
# Remark

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

### show images

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

### Login

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