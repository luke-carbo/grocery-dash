# 🛒 Grocery Dash — CMPT 276 Group 18

> A 2D arcade-style grocery store game built in Java with Swing.  
> Grab every item on your list and reach the exit — without getting caught.

---

## Story

You've got a grocery list, an empty stomach, and zero patience for checkout lines. The plan? Grab everything you need and slip out before the security guard notices. Dodge their patrol routes, collect all the items, and make it to the exit. Easy in theory. Not so easy in practice.

---

## Gameplay

- Navigate a 2D grocery store map
- Collect all items scattered across the store
- Avoid the patrolling security guard
- Reach the exit to win the level
- Get caught — and it's game over

---

## Prerequisites

- Java 17+
- Apache Maven 3.6+

---

## Build & Run

**Navigate to the project directory first (We stored `pom.xml` here):**

```bash
cd grocerygame
```

**Compile:**

```bash
mvn compile
```

**Run (recommended):**

```bash
mvn exec:java -Dexec.mainClass="group18.Main"
```

**Or build and run a JAR:**

```bash
mvn package
java -jar target/grocerygame-1.0-SNAPSHOT.jar
```

---

## Testing

```bash
mvn test
```

---

## Controls

| Key | Action |
|-----|--------|
| `W` `A` `S` `D` | Move |
| `ESC` | Pause |
| `R` | Restart |
| `ENTER` | Start game |
