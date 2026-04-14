package com.sortingapi.models;

/**
 * A record representing a boolean predicate for filtering objects based on a
 * boolean value. The object is either there or not. It is based on whether the
 * user object is currently present or not, as fields being present or not is
 * handled in the ComparisonPredicate with the PRESENT comparison type.
 */
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

  /**
   * Override the toString method to provide a string representation of the
   * BooleanPredicate.
   * This was added for debugging purposes to easily visualize the contents of the
   * predicate when printed.
   */
  @Override
  public String toString() {
    return "BooleanPredicate{" +
        "boolValue='" + boolValue + '\'' +
        '}';
  }

}
