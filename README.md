# Filter API

A simple Maven-based Java project for Filtering User objects created as Map<String, String>. We implemented
a sealed interface and 3 Record classes based on the interface

## Project Structure

```
filter-api/
├── pom.xml                                    # Maven configuration
├── src/
│   ├── main/java/com/filterapi/
│   │   └── api
|   |       └── Filter.java                 # Main Filtering API
|   |   └── enums
|   |       └── Comparisons.java            # Enums for the Comparison Predicate Types
|   |       └── Operators.java              # Enums for the Logical Predicate Types
|   |   └── models
|   |       └── BooleanPredicate.java       # The Boolean Predicate used for the Filter object
|   |       └── ComparisonPredicate.java    # The Comparison Predicate used for the Filter object - the primary functional predicate
|   |       └── FilterPredicate.java        # The Filter Interface that the other three Predicates are built around
|   |       └── LogicalPredicate.java       * The Logical Predicate used for the Filter object - the primary grouper and builder predicate
│   └── test/java/com/filterapi/
│       └── BooleanPredicateTests.java      # Unit tests for just the BooleanPredicate
|       └── ComparisonPredicateTests.java   # Unit tests for just the ComparisonPredicate
|       └── LogicalPredicateTests.java      # Unit tests for the LogicalPredicate incorporating the two other predicates
|       └── testingUtils.java               # testing utils, mainly centered around creating data.  If the project gets bigger, we will create more folders in the test portion.
└── README.md                               # Self Referencing... You are here, go back to the beginning and start again...
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
mvn test -Dtest=ComparisonPredicateTests#testNameHere
```

## Cleaning Build Artifacts

```bash
mvn clean
```
