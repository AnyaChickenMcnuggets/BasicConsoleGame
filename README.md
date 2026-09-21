# The Hollow Crown

A text-based, DnD-flavored solo campaign in the console. Create a hero, pick a path (Might or Guard) and grow it into a branching skill tree, gear up with real weapons and armor, take on quests from the villagers of Millhaven, and fight your way through the Greywood Marches to the climactic battle at the Splintered Peaks.

Combat rolls a d20 to hit against armor class, with damage dice on a hit and a crit on a natural 20 — closer to tabletop DnD than a flat stat formula.

## Requirements

- Java 17+

## Run

```bash
./gradlew run
```

## Build

```bash
./gradlew build
```

Compiles, runs the test suite, and produces a runnable jar at `build/libs/frpg-<version>.jar`.

## Run the built jar

```bash
java -jar build/libs/frpg-1.0.0.jar
```

## Releases

A built jar for each release is available under [Releases](../../releases).

## Save/load

Progress is saved to `saves/save.properties` in the working directory (gitignored). On launch, an existing save offers to continue or start a new journey.

## Tests

```bash
./gradlew test
```
