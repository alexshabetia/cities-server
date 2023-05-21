# Cities server

## Run with Docker Compose

Prerequisites: Docker installed.

In the project directory, you can run:

#### `docker-compose-up`

Open [http://localhost:8080](http://localhost:8080) to view it in your browser.

`.env` file is used to set up environment variables for database connection inside Docker.

## Run with Gradle

Prerequisites: JDK 17 installed. PostgreSQL running on localhost:5432. "cities" database exists. User with password and login "postgres" has access to "cities" database.

Environment variables `DB_HOST, DB_PORT, DB_DATABASE, DB_USER, DB_PASSWORD` can override database connection properties.

In the project directory, you can run:

#### `./gradlew run`


API is available at [http://localhost:8080](http://localhost:8080) when application is running.

## Swagger

Swagger is available at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) when application is running.

For city editing Basic Authorization in API requests is required. Login: "admin", password: "admin".
