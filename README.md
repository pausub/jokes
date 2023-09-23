# Jokes API

A Spring Boot RESTful application that serves as a proxy to a https://api.chucknorris.io/. With this proxy, users can retrieve random jokes, obtain a list of joke categories, and search for jokes based on specific criteria.

## Features
 - Get a Random Joke: Retrieve a random joke from the jokes database, might be filtered by joke category.
 - List Joke Categories: Get a list of all available joke categories.
 - Search for Jokes: Search for jokes based on specific keywords.

## Useful endpoints for development (most should be disabled in prod)

 - Swagger: http://localhost:8080/jokes/swagger-ui/index.html
 - Spring boot actuator endpoints: http://localhost:8080/jokes/actuator