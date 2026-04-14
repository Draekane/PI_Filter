# Filter API

A simple Maven-based Java project for Filtering User objects created as Map<String, String>. We implemented
a sealed interface and 3 Record classes based on the interface

## Notes for Tasks List:

1. The Ability to determine whether or not a filter meatches a given resource ✅
2. Support Boolean, Logical, and Comparison Operators ✅
3. The ability to programmatically construct arbitrarily complex filters (see tests) ✅
4. String representation/parsing - `toString()` / `parseFromString()` as a start ✅
5. Extensibility:<br/>
   a. FilterPredicate interface allows for adding new Predicate types, and that would allow including new types of predicates, along with updates to LogicalPredicate as it allows joining of other predicate types.<br/>
   b. As long as a third party can send data in a `Map<String, String>` format, this Filter should be able to handle it. We focused on using the example of a user, but it should work with any `Map<String, String>` type object. Third parties can always use the ToString to get the content of a `Filter` or `Predicate`, but we could potentially expose more of the internals with additional gets (see `Filter` for the example.)

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
