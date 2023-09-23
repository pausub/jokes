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

#### To run the app in container:

```shell
./gradlew build
docker build -t jokes .
docker run -p 8080:8080 jokes
```

## Useful endpoints for development (most should be disabled in prod)

 - Swagger: http://localhost:8080/jokes/swagger-ui/index.html
 - Spring boot actuator endpoints: http://localhost:8080/jokes/actuator