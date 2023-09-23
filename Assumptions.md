#### Monitoring:
Exposed via Spring actuator endpoints TODO:

#### Logging: 
Request/Response logging - spring default
Additional logging on external operations request/responses

#### Caching: 
TODO:

#### Testing:
Prefer having unit, api, e2e/integration tests. E2e vs integration testing (mocking or not of 3rd party services) is a controversial topic and requires further discussions. It would be necessary to implement load testing before any optimization attempts.

#### Containerization:
Single docker container used for running the application. Real world applications, would most likely use additional containers for database, caching, message brokers, monitoring or else. Omitted for simplification reasons. Would use Docker Compose to manage multiple containers.

#### Version control
Prefer using Git feature branch workflow. Jokes project too simple to properly demonstrate

#### HTTP client:
Used Spring RestTemplate for simplicity, in prod would use Spring WebClient, would consider non-blocking IO

#### Endpoint Design:
Simple design according to REST standards, mimic api.chucknorris.io

#### Scalability and Performance:
TODO:

#### External API Stability:
TODO:

#### Project structure
Feature based (classes grouped by feature) over structure based (packages grouped by application layer) packaging for better code organization when code base grows

#### Data Validation/Sanitation:
Assume that it's not necessary to validate user input nor sanitize responses as jokes service is simple proxy to api.chucknorris.io

#### Error Handling:
Simple error handling by returning 500 on server error or 502 on 3rd party server error. 4XX handled by standard Spring exception handling
