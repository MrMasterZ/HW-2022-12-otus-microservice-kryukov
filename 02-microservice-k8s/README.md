## Homework №2 (theme: "kubernetes") Kryukov Sergey Alexandrovich on the course otus "Microservice Architecture"

### Task
* Wrap the application in a docker image and put it on Dockerhub
* Application: GET /health/ (port: 8000)    RESPONSE: {"status": "OK"}
* Write manifestos (Deployment, Service, Ingress) for deploying this service to k8s
* In Deployment can be specified Liveness, Readiness probes
* Number of replicas must be at least 2
* Image of container must be specified from Dockerhub.
* host in the ingress must be arch.homework
* After the manifestos are applied: GET http://arch.homework/health must give {"status": "OK"}
* In ingress.yaml must have the rule for forwarding all requests /otusapp/kryukov to service,
* i.e. GET http://arch.homework/otusapp/kryukov/health (port: 8000)    RESPONSE: {"status": "OK"}

### Used technologies
* Docker (Docker version 24.0.4, build 3713ee1)
* Dockerfile
* Spring boot (3.0.1)
* spring-boot-starter-web
* maven (3.8.1)
* java-17
* kubernetes (minikube version: v1.31.0)
* kubectl create namespace m && helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx/ && helm repo update && helm install nginx ingress-nginx/ingress-nginx --namespace m -f resource/nginx-ingress.yaml

### Command to build app docker-image
* docker build -t 02-microservice-k8s:v1 ./app

### Command to create (based on docker-image) and start docker-container
* docker run -dti -p 8000:8000 --name health 02-microservice-k8s:v1

### Command to create (based on docker-image) and start docker-container (on Windows)
* winpty docker run -dti -p 8000:8000 --name health 02-microservice-k8s:v1

### Deploy the pplication
* cd HW-2022-12-otus-microservice-kryukov/02-microservice-k8s/inventory
* kubectl apply -f .

### Delete the application
* kubectl delete deployment otus-02-microservice-k8s-deployment
* kubectl delete service otus-02-microservice-k8s-service
* kubectl delete ingress otus-02-microservice-k8s-ingress

### curl
* curl http://arch.homework/otusapp/kryukov/health
* curl http://arch.homework/health
