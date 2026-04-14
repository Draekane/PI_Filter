package com.sortingapi.models;

import java.util.Map;

import com.sortingapi.enums.Comparisons;

import org.apache.commons.lang3.math.NumberUtils;

public record ComparisonPredicate(Comparisons type, String field, String value) implements FilterPredicate {

    // Constructor with no value
    public ComparisonPredicate(String type) {
        this(Comparisons.fromString(type), null, null);
    }

    // Constructor with type and field reference
    public ComparisonPredicate(String type, String field) {
        this(Comparisons.fromString(type), field, null);
    }

    // Constructor with int value and field reference
    public ComparisonPredicate(String type, String field, int value) {
        this(Comparisons.fromString(type), field, Integer.toString(value));
    }

    // Constructor with all values, but Comparison as string
    public ComparisonPredicate(String type, String field, String value) {
        this(Comparisons.fromString(type), field, value);
    }

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

    public String toString() {
        return "ComparisonPredicate{" +
                "type=" + type +
                ", field='" + field + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

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

    private boolean isPresent(Map<String, String> user) {
        return user.containsKey(field);
    }

    private boolean matchesRegex(Map<String, String> user) {
        String fieldValue = user.get(field);
        if (fieldValue == null) {
            return false;
        }

        boolean result = fieldValue.matches(this.value);

        return result;
    }

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

    private boolean isLessThan(Map<String, String> user) {
        String fieldValue = user.get(field);
        if (fieldValue == null || !NumberUtils.isParsable(fieldValue) || !NumberUtils.isParsable(this.value)) {
            return false;
        }

        double fieldNum = Double.parseDouble(fieldValue);
        double valueNum = Double.parseDouble(this.value);
        return fieldNum < valueNum;
    }

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
