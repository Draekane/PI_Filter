package com.filterapi.enums;

public enum SearchType {
  LEVENSHTEIN, JARO, PHONETIC;

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
  public static SearchType fromString(String searchType) {
    return switch (searchType.toLowerCase()) {
      case "levenshtein" -> SearchType.LEVENSHTEIN;
      case "jaro" -> SearchType.JARO;
      case "phonetic" -> SearchType.PHONETIC;
      default -> throw new IllegalArgumentException("Invalid search type: " + searchType);
    };
  }
}
