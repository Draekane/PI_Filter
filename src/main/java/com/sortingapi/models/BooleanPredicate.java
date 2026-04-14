package com.sortingapi.models;

public record BooleanPredicate(String boolValue) implements FilterPredicate {
    /**
     * Evaluate the boolean predicate based on the provided context. In this case,
     * it simply returns the boolean value of the predicate.
     * If the context is null, it returns false. Otherwise, it parses the boolValue
     * string to a boolean and returns that value.
     * NOTE: This can take any string value, but only "true" (case-insensitive) will
     * return true. All other values will return false.
     */
    @Override
    public boolean evaluate(Object context) {
        if (context == null) {
            return false;
        } else {
            return Boolean.parseBoolean(boolValue);
        }
    }

    @Override
    public String toString() {
        return "BooleanPredicate{" +
                "boolValue='" + boolValue + '\'' +
                '}';
    }

}
