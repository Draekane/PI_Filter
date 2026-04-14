package com.sortingapi.models;

public sealed interface FilterPredicate permits BooleanPredicate, LogicalPredicate, ComparisonPredicate {
    boolean evaluate(Object context);

    String toString();
}
