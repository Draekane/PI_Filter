package com.sortingapi.enums;

public enum Comparisons {
    PRESENT, EQUALS, LESS_THAN, GREATER_THAN, MATCHES_REGEX;

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
