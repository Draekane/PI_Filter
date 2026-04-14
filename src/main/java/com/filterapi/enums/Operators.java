package com.filterapi.enums;

/**
 * Enum representing logical operators for filtering criteria.
 * It includes AND, OR, and NOT operators.
 */
public enum Operators {
  AND, OR, NOT;

  /**
   * Converts a string representation of an operator to the corresponding enum
   * value.
   * 
   * @param operator the string representation of the operator (case-insensitive)
   * @return the corresponding Operators enum value
   * @throws IllegalArgumentException if the input string does not match any of
   *                                  the defined operators
   */
  public static Operators fromString(String operator) {
    return switch (operator.toLowerCase()) {
      case "and" -> AND;
      case "or" -> OR;
      case "not" -> NOT;
      default -> throw new IllegalArgumentException("Invalid operator: " + operator);
    };
  }
}
