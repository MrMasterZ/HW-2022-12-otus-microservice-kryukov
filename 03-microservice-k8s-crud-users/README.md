## Homework №3 (theme: "kubernetes with using configmaps/secrets and helm") Kryukov Sergey Alexandrovich on the course otus "Microservice Architecture"

### Task
* create simplest RESTful CRUD application for creating, deleting, viewing and updating users, using database
* API - https://app.swaggerhub.com/apis/otus55/users/1.0.0
* Application configuration must be stored in Configmaps.
* Database accesses must be stored in Secrets.
* Initial migrations of database must be formalized as Job
* host in the ingress must be arch.homework

### Used technologies
* Docker (Docker version 24.0.4, build 3713ee1)
* Dockerfile
* Spring boot (3.0.1)
* spring-boot-starter-web
* maven (3.8.1)
* java-17
* kubernetes (minikube version: v1.31.0)
* helm
* openapi-3.0.0
* liquibasei-4.9.1
* postgresql-15.5.9
* postman
* newman

### Deploy the pplication
* 1) start docker
* 2) deploy.sh <ip-v4> or manual about deploy

### port-forward
* POD_NAME=`kubectl get pods -A -n m | grep 03-microservice-k8s-crud-users | awk ' { print $2 } ' | head -1`; kubectl port-forward $POD_NAME 8080:8000 -n m

### newman
* newman run --env-var url=http://arch.homework:8000 otus-micr-arch-hw-3.postman_collection.json
