# Testing Example Using Spring
This project is a sample of suggestions for testing a multi-tier application. It is built on Java 17.

## Setup
The project assumes a MariaDB database on port 3306 called "demo" with a root user with no password. This can be configured in SpringJdbcConfig. The database must exist with at least 1 record or the tests will fail.

`demo-database.sql` is the script to set up the database.

## Running
Build the project using `mvn clean install` which will run the tests.

Run the project with `mvn spring-boot:run`.

`GET localhost:8080/demo/person/{id}` is the only possible request.