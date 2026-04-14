package com.filterapi.models;

import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import com.filterapi.enums.Comparisons;

/**
 * A record representing a comparison predicate for filtering objects based on a
 * field value.
 */
public record ComparisonPredicate(Comparisons type, String field, String value) implements FilterPredicate {

  /**
   * Constructor with only type, defaults field and value to null. This is useful
   * for the PRESENT comparison type, which only requires a field reference and no
   * value.
   */
  public ComparisonPredicate(String type) {
    this(Comparisons.fromString(type), null, null);
  }

  /**
   * Constructor with type and field reference, defaults value to null. This is
   * useful for the PRESENT comparison type, which only requires a field reference
   * and no value. For other comparison types, the value can be set to null, but
   * it may cause the evaluation to return false since the value is required for
   * those comparisons.
   */
  public ComparisonPredicate(String type, String field) {
    this(Comparisons.fromString(type), field, null);
  }

  /**
   * Constructor with type, field reference, and integer value. This is useful for
   * comparison types that require a numeric value.
   */
  public ComparisonPredicate(String type, String field, int value) {
    this(Comparisons.fromString(type), field, Integer.toString(value));
  }

  /**
   * Constructor with all values, but Comparison as string.
   */
  public ComparisonPredicate(String type, String field, String value) {
    this(Comparisons.fromString(type), field, value);
  }

  /**
   * Evaluate the predicate based on the provided context. The context is expected
   * to be a Map<String, String> representing user attributes. The method checks
   * if the context is an instance of Map<?, ?> and then attempts to cast it to
   * Map<String, String>. If the cast is successful, it calls the evaluate method
   * that takes a Map<String, String> as an argument. If the context is not a Map
   * or if the cast fails, it returns false.
   * 
   * @param context The context to evaluate the predicate against, expected to be
   *                a Map<String, String>.
   * @return true if the predicate evaluates to true based on the context, false
   *         otherwise.
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean evaluate(Object context) {
    if (context instanceof Map<?, ?> user) {
      try {
        return evaluate((Map<String, String>) user);
      } catch (ClassCastException e) {
        System.err.println("Invalid context type for ComparisonPredicate: " + e.getMessage());
        return false;
      }
    } else {
      return false;
    }
  }

  /**
   * Override the toString method to provide a string representation of the
   * ComparisonPredicate.
   * This was added for debugging purposes to easily visualize the contents of the
   * predicate when printed.
   */
  @Override
  public String toString() {
    return "ComparisonPredicate{" +
        "type=" + type +
        ", field='" + field + '\'' +
        ", value='" + value + '\'' +
        '}';
  }

  /**
   * Evaluate the predicate based on the provided user attributes.
   *
   * @param user The user attributes to evaluate the predicate against.
   * @return true if the predicate evaluates to true based on the user attributes,
   *         false otherwise.
   */
  public boolean evaluate(Map<String, String> user) {
    return switch (type) {
      case Comparisons.PRESENT -> isPresent(user);
      case Comparisons.EQUALS -> isEqual(user);
      case Comparisons.LESS_THAN -> isLessThan(user);
      case Comparisons.GREATER_THAN -> isGreaterThan(user);
      case Comparisons.MATCHES_REGEX -> matchesRegex(user);
      default -> false;
    };
  }

  /**
   * Check if the specified field is present in the user attributes.
   * 
   * @param user The user attributes to check.
   * @return true if the field is present, false otherwise.
   */
  private boolean isPresent(Map<String, String> user) {
    return user.containsKey(field);
  }

  /**
   * Check if the specified field's value matches the regex pattern provided in
   * the value.
   * 
   * @param user The user attributes to check.
   * @return true if the field's value matches the regex pattern, false otherwise.
   */
  private boolean matchesRegex(Map<String, String> user) {
    String fieldValue = user.get(field);
    if (fieldValue == null) {
      return false;
    }

    boolean result = fieldValue.matches(this.value);

    return result;
  }

  /**
   * Check if the specified field's value is equal to the value provided.
   *
   * @param user The user attributes to check.
   * @return true if the field's value is equal to the provided value, false
   *         otherwise.
   */
  private boolean isEqual(Map<String, String> user) {
    String fieldValue = user.get(field);
    if (fieldValue == null) {
      return false;
    }

    if (NumberUtils.isParsable(this.value) && NumberUtils.isParsable(fieldValue)) {
      double fieldNum = Double.parseDouble(fieldValue);
      double valueNum = Double.parseDouble(this.value);
      return fieldNum == valueNum;
    } else {
      return fieldValue.equals(this.value);
    }
  }

  /**
   * Check if the specified field's value is less than the value provided.
   *
   * @param user The user attributes to check.
   * @return true if the field's value is less than the provided value, false
   *         otherwise.
   */
  private boolean isLessThan(Map<String, String> user) {
    String fieldValue = user.get(field);
    if (fieldValue == null || !NumberUtils.isParsable(fieldValue) || !NumberUtils.isParsable(this.value)) {
      return false;
    }

    double fieldNum = Double.parseDouble(fieldValue);
    double valueNum = Double.parseDouble(this.value);
    return fieldNum < valueNum;
  }

  /**
   * Check if the specified field's value is greater than the value provided.
   *
   * @param user The user attributes to check.
   * @return true if the field's value is greater than the provided value, false
   *         otherwise.
   */
  private boolean isGreaterThan(Map<String, String> user) {
    String fieldValue = user.get(field);
    if (fieldValue == null || !NumberUtils.isParsable(fieldValue) || !NumberUtils.isParsable(this.value)) {
      return false;
    }

    double fieldNum = Double.parseDouble(fieldValue);
    double valueNum = Double.parseDouble(this.value);
    return fieldNum > valueNum;
  }
}
