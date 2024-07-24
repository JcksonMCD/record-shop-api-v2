# Record Shop API
Welcome to the Record Shop API! This application is a RESTful API designed to manage a collection of music albums and artists.

## Project Description
The Record Shop API is designed to provide a robust backend for managing a record shop's catalog. It supports operations like creating, reading, updating, and deleting albums and artists. The application is built using Java and Spring Boot, with a focus on clean architecture and best practices.

## Technologies Used
Java 17
Spring Boot 2.6
Hibernate
Spring Data JPA
H2 Database (for development and testing)
JUnit 5 and Mockito (for testing)
Swagger (for API documentation)

## Features
Manage albums and artists with full CRUD operations.
Search and filter albums by various criteria.
Secure API with authentication and authorization.
Exception handling for robust error management.

## API Documentation
API documentation is available through Swagger UI at http://localhost:8080/swagger-ui/index.html.

## API Endpoints
Albums
GET /album - Retrieves a list of albums.
GET /album/{id} - Retrieves details of a specific album by ID.
POST /album - Creates a new album. If artist is not currently in the data base it will also create a new artist.
PUT /album/{id} - Updates an existing album by ID.
DELETE /album/{id} - Deletes an album by ID.
