# 🛒 CMPT 276 Grocery Game — Group 18
 
A 2D arcade-style grocery store game built in Java using Swing.
Avoid the security guard, collect all items, and reach the exit.

## Story
You are a security guard bla bla bla (to be continued)

## Prerequisites

- Java 17+
- Apache Maven 3.6+

## How to Build

Navigate to the `grocerygame` directory (where `pom.xml` is located):

Run the following command:

`mvn compile`

## How to Run
You can either run the game using the Maven command below (Recommended)

**Maven Command**: ```mvn exec:java -Dexec.mainClass="group18.Main"```

Alternatively, you can use Maven to build a jar and run it that way.

```c
mvn package
java -jar target/grocerygame-1.0-SNAPSHOT.jar
```

## How to Test

**In order to run all tests, use the following commands:**

```mvn test```

## Controls

- `WASD` — Move
- `ESC`  — Pause
- `R`    — Restart
- `ENTER` — Start game
