# Pokémon TCGP Builder

## Context

This application is a deck builder for the Pokémon Trading Card Game. It allows users to create, edit, and delete decks,
as well as view and rate decks created by other users. Users can also view all cards in the database, and add them
to their inventory, to keep track of their collection. You can learn more about the application in
the [initial report](docs/INITIAL_REPORT.md) document.

## Prerequisites

To run this application, ensure you have the following installed on your system:

- **Java 17** or higher
- **Maven 4.0+**
- **Docker** 

## How to run

The application runs using the dev mode of Quarkus. To run the application, execute the following command:

```shell
./mvnw quarkus:dev
```

This runs the application in development mode with live reload and launches multiple docker containers. At this step,
you should see the following containers running:

- PostgreSQL database
- Artemis message broker
- Test container

To be able to test the mail sending feature, you need to launch a docker container with a Mailpot server. To do so, 
run the following command:

```shell
docker run -p 11111:1025 -p 8025:8025 ghcr.io/axllent/mailpit
```

If you want to run the container on a different port, you can change the port number in the command above but make sure
to also change the port number in the `application.properties` file.

```properties
quarkus.mailer.port=<chosen port>
```

## Accessing the application

### Web interface

The application interface is available at  [http://localhost:8080](http://localhost:8080). When you reach the interface,
you can go to the login page and either create an account or use the following credentials to log in:
- username: `Edwin`, password: `1234`
- username: `Eva`, password: `1234`
- username: `Rachel`, password: `1234`
- username: `Arthur`, password: `1234`

### Mailpit interface
The mailpit interface is available at [http://localhost:8025](http://localhost:8025). This interface allows you to view 
all the emails sent by the application.