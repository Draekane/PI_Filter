package com.filterapi.models;

/**
 * A sealed interface representing a filter predicate that can be evaluated
 * against an object.
 * This interface is implemented by specific predicate types such as
 * BooleanPredicate, LogicalPredicate, and ComparisonPredicate.
 * Created to limit the types of predicates so that any others couldn't be
 * created without explicitly being added to the permits clause.
 * This allows for better control over the types of predicates used in the
 * filtering API and ensures that only valid predicates are implemented.
 */
public sealed interface FilterPredicate permits BooleanPredicate, LogicalPredicate, ComparisonPredicate {
    boolean evaluate(Object context);

    String toString();
}
