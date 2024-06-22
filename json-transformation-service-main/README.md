# JSON Transformation Service
This service is responsible for transforming JSON data from one format to another. It is built using Spring Boot and uses Jolt (JSON to JSON transformation library written in Java) for transformation.

## Table of Contents
1. [What do I offer?](#what-do-i-offer)
2. [Pre-requisites](#pre-requisites)
3. [Installation Guide](#installation-guide)
4. [Running the service in IntelliJ IDEA](#running-the-service-in-intellij-idea)
5. [API Documentation](#api-documentation)
6. [Transformation Rules State Machine](#transformation-rules-state-machine)
7. [Connecting to the database](#connecting-to-the-database)
8. [Running the checkstyle](#running-the-checkstyle)
9. [Running the tests](#running-the-tests)
10. [Open source tools used](#open-source-tools-used)

## What do I offer?
- Transform JSON data from one format to another using Jolt.
- Support versioning of transformations.
- Store transformation rules in a database.
- Store the audit logs of the transformation rules.
- Provide an API to transform JSON data using the stored transformation rules.

## Pre-requisites
- Java 11
- Maven
- Docker
- IntelliJ IDEA (or any other Java IDE)

## Installation Guide
Refer to the [Installation Guide](INSTALLATIONS.MD) to install the pre-requisites.

## Running the service in IntelliJ IDEA
1. Clone the repository (if using git) or download the zip file of the repository, unzip it and open the project in IntelliJ IDEA
2. Open the project in IntelliJ IDEA
3. Run the following command on the terminal to start the infrastructure services
   ```docker-compose up -d```
4. Run the `Database` class in migration module to create the database tables
5. Run the `JsonTransformationServiceApplication` class
6. The service will start at `http://localhost:8080`
7. You can access the Swagger UI at `http://localhost:8080/json-transformation-service/swagger-ui.html`
8. Run the following command or browse the following URL in the browser to check if the service is up and running
   ```curl http://localhost:8080/json-transformation-service/ping```
9. You should see the following response
   ```json
   "PONG"
   ```
10. Run the following command to stop the infrastructure services after you are done
   ```docker-compose down```


## API Documentation
You can access the Swagger UI at `http://localhost:8080/json-transformation-service/swagger-ui.html`

The service provides the following APIs (Learn more about the APIs [here](https://spring.io/guides/gs/rest-service/)):
- `GET /json-transformation-service/ping`: This API is used to check if the service is up and running.
- `POST /json-transformation-service/v1/json-transformation/transform`: This API is used to transform JSON data from one format to another using the stored transformation rules.
- `POST /json-transformation-service/v1/transformation-rules/create`: This API is used to  upload the transformation rules from a file.
- `GET /json-transformation-service/v1/transformation-rules/all-by-name`: This API is used to get the transformation rule details by the rule name.
- `PUT /json-transformation-service/v1/transformation-rules/change-status`: This API is used to change the status of the transformation rule.

## Transformation Rules State Machine
The transformation rules can have the following states:
- `INACTIVE`: The transformation rule is inactive and cannot be used for transformation.
- `ACTIVE`: The transformation rule is active and can be used for transformation.

The transformation rules can transition between the following states:
- `INACTIVE` -> `ACTIVE`: The transformation rule is activated.
- `ACTIVE` -> `INACTIVE`: The transformation rule is deactivated.

When a transformation rule is uploaded, it is in the `INACTIVE` state by default. The transformation rule can be activated by changing the status to `ACTIVE`.
At any point only one transformation rule  with the same name can be in the `ACTIVE` state.

When new transformation rules are uploaded, the system checks whether the transformation rule with the same name is already present in the system.
If the transformation rule is present, the system the new tranfomration rule verson greater than 
the existing transformation rule version by incrementing the version by 1. If the transformation 
rule is not present, the system creates a new transformation rule with version 0.

## Connecting to the database
You can access the database by using the database client using any database client like Postico, 
DBeaver, etc. using the following credentials:
- Host: `localhost`
- Port: `5432`
- Database: `json_transformation_service`
- Username: `postgres`
- Password: `admin`

Note : Make sure the database is up and running on docker before connecting to the database.

![service_docker_dashboard_screenshot.png](docs%2Fservice_docker_dashboard_screenshot.png)

## Running the checkstyle
Run the following command to run the checkstyle. Learn more about the checkstyle [here]
(https://checkstyle.org/) why it is important to follow the checkstyle.
```mvn checkstyle:check```

## Running the tests
Run the following command to run the tests
```mvn test```

## Open source tools used
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring REST](https://spring.io/guides/gs/rest-service/)
- [Liquibase](https://www.liquibase.org/)
- [Jolt](https://github.com/bazaarvoice/jolt)
- [PostgreSQL](https://www.postgresql.org/)
- [Swagger](https://swagger.io/)
- [Project Lombok](https://projectlombok.org/)
- [JUnit 5](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)
- [Prometheus](https://prometheus.io/)
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
