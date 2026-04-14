# Sorting API

A simple Maven-based Java project for sorting algorithms with unit tests.

## Project Structure

```
sorting-api/
├── pom.xml                                    # Maven configuration
├── src/
│   ├── main/java/com/sortingapi/
│   │   └── SortingAPI.java                   # Main sorting algorithms
│   └── test/java/com/sortingapi/
│       └── SortingAPITest.java              # Unit tests
└── README.md
```

## Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

## Building the Project

```bash
mvn clean compile
```

## Running Tests

```bash
mvn test
```

## Running a Specific Test

```bash
mvn test -Dtest=SortingAPITest#testNameHere
```

## Cleaning Build Artifacts

```bash
mvn clean
```
