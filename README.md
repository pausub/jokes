# Jokes API

A Spring Boot RESTful application that serves as a proxy to a https://api.chucknorris.io/. With this proxy, users can retrieve random jokes, obtain a list of joke categories, and search for jokes based on specific criteria.

## Features
 - Get a Random Joke: Retrieve a random joke from the jokes database, might be filtered by joke category.
 - List Joke Categories: Get a list of all available joke categories.
 - Search for Jokes: Search for jokes based on specific keywords.

## Build & Run

#### Make sure you have the following tools installed on your machine:
- JDK
- Docker runtime
- Git

#### To run the app in container, execute in root directory:
```shell
./gradlew build
docker build -t jokes -f docker/Dockerfile .
docker run -p 8080:8080 jokes
```

#### To run the app in container together with prometheus and grafana, execute in root directory:
```shell
./gradlew build
docker-compose -f docker/docker-compose.yml up --build
```


## Useful endpoints for development (most should be disabled in prod)

 - Swagger: http://localhost:8080/jokes/swagger-ui/index.html
 - Spring boot actuator endpoints: http://localhost:8080/jokes/actuator
 - Prometheus: http://localhost:9090/
 - Grafana: http://localhost:3000/ (admin/admin)

## Design Assumptions:
[Assumptions.md](Assumptions.md)

## And most importantly
Don't forget the man who made this all possible!

![Chuck](./readme-resources/chuck.jpeg)