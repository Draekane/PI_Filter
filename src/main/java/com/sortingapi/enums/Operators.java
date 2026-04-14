package com.sortingapi.enums;

public enum Operators {
    AND, OR, NOT;

    public static Operators fromString(String operator) {
        return switch (operator.toLowerCase()) {
            case "and" -> AND;
            case "or" -> OR;
            case "not" -> NOT;
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }
}
