FROM maven:3.8.1-openjdk-17
ARG PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY ./ $PROJECT_DIR/
RUN mvn clean package -DskipTests

FROM openjdk:17-oracle
MAINTAINER Sergey Kryukov  Kryukov.S.A@yandex.ru
ARG PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/target/02-microservice-k8s-1.0.0.jar $PROJECT_DIR/
CMD java -jar /opt/project/02-microservice-k8s-1.0.0.jar
