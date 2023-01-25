## Homework №1 (theme: "Docker") Kryukov Sergey Alexandrovich on the course otus "Microservice Architecture"

### Task
* Wrap the application in a docker image and put it on Dockerhub
* Application: GET /health/ (port: 8000)    RESPONSE: {"status": "OK"}

### Used technologies
* Docker
* Dockerfile
* Spring boot (3.0.1)
* spring-boot-starter-web
* maven (3.8.1)
* java-17

### Command to build app docker-image
* docker build -t 01-microservice-docker:v1 ./app

### Command to create (based on docker-image) and start docker-container
* docker run -dti -p 8000:8000 --name health 01-microservice-docker:v1

### Command to create (based on docker-image) and start docker-container (on Windows)
* winpty docker run -dti -p 8000:8000 --name health 01-microservice-docker:v1
