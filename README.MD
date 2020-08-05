# Codechallenge Backend Bank Application

### Introducction
The goal of this code challenge is to create a microservice using Java and any framework that other appropriate.

All the information about the Api endpoints, objetcs, etc, was documented with Swagger in the following url http://localhost:8080/swagger-ui.html.

### Technological stack
* Spring Boot 2.3.1, Maven
* Java 1.8
* DDD and Hexagonal Arquitecture
* Testing: Junit5, Mockito
* Database: PostgreSQL, Spring Data JPA, Flyway
* Docker
* Swagger

### Run the microservice:
1. **Build the app**
```
mvn clean install
```

2. **Run docker-compose to start Spring Boot Application with PostgreSQL**
```
docker-compose up
```

### Run tests
```
mvn test
```

### Test the application with swagger:
1. **Open Swagger and call the endpoints based on the documentation.**
http://localhost:8080/swagger-ui.html