# example-cdi

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Exercise CDI

Use Jakarta CDI to represent a library

Implement a **Book** object with a **title**, an **author**, and, optionally, a **borrower**.

Implement a Publisher bean that **publishes** two books:
- "The Lord of the Rings" by J.R.R. Tolkien qualified as "Heroic Fantasy"
- "Dune" by Frank Herbert qualified as "Science Fiction"

Implement a Shelf bean that **holds** books for the library.
- The shelf should be filled with the "Heroic Fantasy" books when it is created.
- The shelf should offer a method to retrieve books by title.

Implement a **Librarian** bean that offers services to **borrow** and **return** books.
- A client should be able to borrow a book to the librarian.
- A client should be able to return a book to the librarian.
- The librarian has access to the shelf.

Whenever a book is removed from the shelf, it should be stamped and marked as borrowed.
Implement this behaviour with a decorator on the shelf.

Whenever a book is borrowed or returned, a bell should ring in the library.
Implement this behaviour with an interceptor on the librarian.

The library **Manager** is a control freak and asks to be notified whenever a book is returned.
Implement this behaviour with an observer triggered by the librarian.


## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/example-cdi-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
