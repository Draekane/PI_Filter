package com.filterapi.enums;

/**
 * Enum representing the different types of comparisons that can be performed in
 * the sorting API.
 * Each enum constant corresponds to a specific comparison operation that can be
 * used in the sorting criteria.
 */
public enum Comparisons {
  PRESENT, EQUALS, LESS_THAN, GREATER_THAN, MATCHES_REGEX;

  /**
   * Converts a string representation of a comparison type to its corresponding
   * enum constant.
   * The input string is case-insensitive and should match one of the defined
   * comparison types.
   * 
   * @param comparison the string representation of the comparison type
   * @return the corresponding Comparisons enum constant
   * @throws IllegalArgumentException if the input string does not match any of
   *                                  the defined comparison types
   */
  public static Comparisons fromString(String comparison) {
    return switch (comparison.toLowerCase()) {
      case "present" -> PRESENT;
      case "equals" -> EQUALS;
      case "less_than" -> LESS_THAN;
      case "greater_than" -> GREATER_THAN;
      case "matches_regex" -> MATCHES_REGEX;
      default -> throw new IllegalArgumentException("Invalid comparison type: " + comparison);
    };
  }
}
