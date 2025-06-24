# Quarkus REST Playground

This project demonstrates a basic REST API built with [Quarkus](https://quarkus.io/). It exposes a few example resources (e.g. `DriverResource`, `CompanyResource`) that are backed by Hibernate ORM and documented via OpenAPI/Swagger UI.

The application expects a Microsoft SQL Server database. The connection URL is provided via the `DATABASE_URL` environment variable. No schema creation is done automatically, so the database must already contain the required tables.

## Prerequisites

- Java 17
- Maven 3.8+
- A running SQL Server instance with the schema expected by the application. Set the connection URL through the `DATABASE_URL` environment variable, for example:

```bash
export DATABASE_URL="jdbc:sqlserver://localhost:1433;databaseName=mydb;user=dbuser;password=dbpass"
```

## Running the application

In development mode, Quarkus provides live reload and a Swagger UI. Start the app with:

```bash
mvn quarkus:dev
```

The REST endpoints will be available under `http://localhost:8080`. Swagger UI is reachable at `http://localhost:8080/q/swagger-ui`. When deploying to production you can build a runnable JAR using `mvn package` and then run `java -jar target/quarkus-app/quarkus-run.jar`.

## Project structure

```
.
├── src/main/java/org/example/    # Application code (entities and REST resources)
├── src/main/resources            # Configuration files
└── pom.xml                       # Maven configuration
```

This repository is intended as a playground for experimenting with Quarkus and its REST capabilities. Feel free to clone it and adapt it to your needs.
