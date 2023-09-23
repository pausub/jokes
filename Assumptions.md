#### Monitoring:
Exposed Spring Boot actuator endpoints
Micrometer/Prometheus/Grafana stack with examples of persisted datasource and dashboard

#### Logging: 
Request/Response logging - spring default
Additional logging on external operations request/responses

#### Caching: 
Implemented simple in memory caching for `getCategories` and `searchJokes` operations. For production application other cache providers could be used. Features like persistence, overflow to disk, horizontal scalability, advanced configuration and others could be considered.

#### Testing:
Prefer having unit, api, e2e/integration tests. E2e vs integration testing (mocking or not of 3rd party services) is a controversial topic and requires further discussions. It would be necessary to implement load testing before any optimization attempts. Implemented BDD and E2E tests for demonstration purposes. In prod would probably choose one or another.

#### Containerization:
Containers for application, prometheus and grafana. Real world applications would most likely use additional containers for databases, caching, message brokers, monitoring or else.

#### Version control:
Prefer using Git feature branch workflow. Jokes project too simple to properly demonstrate.

#### HTTP client:
Used Spring RestTemplate for simplicity, in prod would use Spring WebClient, would consider non-blocking IO.

#### Endpoint Design:
Simple design according to REST standards, mimic api.chucknorris.io.

#### Scalability and Performance:
In order to measure application performance and to evaluate implemented optimisations Load/Stress tests should be implemented (could use Apache JMeter, Gatling or similar).
Also caching, load balancing, non-blocking io. No database integration makes it easier to scale the application.

#### Rate limiting:
Would be a good idea to consider request rate limiting per user and per application using Bucket4j or Guava's RateLimiter.

#### External API Stability:
Depending on requirements - async processing, retry strategies, queuing of requests could be implemented to reduce failure to respond. 

#### Project structure:
Feature based (classes grouped by feature) over structure based (packages grouped by application layer) packaging for better code organization when code base grows.

#### Data Validation/Sanitation:
Assume that it's not necessary to validate user input nor sanitize responses as jokes service is simple proxy to api.chucknorris.io.

#### Error Handling:
Simple error handling by returning 500 on server error or 502 on 3rd party server error. 4XX handled by standard Spring exception handling.
