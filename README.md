# Booking Case Study
 Booking Case Study for JustLife
 
## Tech Stack

* Java: 11
* Database: MySQL
* Framework: Spring Boot
* Testing Framework: Junit & Mockito6cfj mökt
* Build Automation Tool: Maven
* Data Access: Spring Data JPA
* DB Versioning: Flyway
* Documentation: Spring Doc (Open Api v3 - Swagger)

## How to run locally
Only need to create an empty schema called booking. Upon running the code Flyway will manage the creation of tables. Tests will run regardless.

Example data is in resources/data.sql. It will not be automatically inserted since it is mainly for in-memory database.

## How to Use Easily
While this is a Rest API implementation and therefore lacks any GUI, SpringDoc provides a way to more easily use the endpoints. http://localhost:8080/ will redirect to the SpringDoc page. IMPORTANT: For body, SpringDoc sents its requests with newline character which is not accepted by the application. Request body should be edited to be a single line for the request to be accepted.
