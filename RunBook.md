# Setup & Infra RunBook
## Pre-requsive
- WSL or UNIX env
- k3d installed in path
- kubectl installed in path
- docker installed in path
  - disabled containerd, as of k3d v5.8.3, it doesn't work with containerd image 
- added `127.0.0.1 keycloak.local` to /etc/hosts file
- installed JDK 24 in path
- mvn installed in path
- 

# Init 
## Automated init 
`./devops/init-cluster.bash`

## Manual Step
- create or start k3d cluster
  - `k3d cluster create showcase-cluster --registry-create showcase-cluster-registry:0.0.0.0:5000 -p "8081:80@loadbalancer"` create a local cluster in k3d
    - it creates registry in port 5000
    - it creates a port mapping from 8081 (host port) to port 80 (traefik, ingress controller port)
  - or just `k3d cluster start showcase-cluster` to start cluster
- app build (todo: refactor to a script)
  - cd to individual app folder
  - `docker build -t showcase/user-profile-service:v1.0 .`
  - k3d image import showcase/user-profile-service:v1.0 -c showcase-cluster
- cd `db`, `docker-compose up` to spin up db in docker
- cd ../cluster (todo: refactor to a script)
  - docker pull quay.io/keycloak/keycloak:26.2.5
  - k3d image import quay.io/keycloak/keycloak:26.2.5 -c showcase-cluster
  - kubectl apply -f keycloak-deployment.yaml
  - kubectl apply -f ingress.yaml
  - added `127.0.0.1 keycloak.local` to /etc/hosts file
  - verify changes in http://keycloak.local:8081

> Remark: all this is work in WSL as docker is bridging the connection between host machine and wsl

# Start App
run `./devops/start-cluster.bash`