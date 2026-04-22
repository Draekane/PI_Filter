package com.filterapi.models;

import com.filterapi.enums.SearchType;

import java.util.Map;

import org.apache.commons.text.similarity.LevenshteinDistance;

public record SearchPredicate(SearchType type, String field, String value, double maxDistance)
    implements FilterPredicate {

  public SearchPredicate(String type, String field, String value, double maxDistance) {
    this(SearchType.fromString(type), field, value, maxDistance);
  }

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

  public boolean evaluate(Map<String, String> context) {
    String fieldValue = context.get(field);
    if (fieldValue == null) {
      return false;
    }

    return switch (type) {
      case LEVENSHTEIN -> evaluateLevenshtein(value, fieldValue);
      case JARO -> fieldValue.contains(value);
      case PHONETIC -> fieldValue.startsWith(value);
      default -> false;
    };
  }

  @Override
  public FilterPredicate parseFromString(String input) {
    // Format: SearchPredicate{type=<TYPE>, field='<FIELD>', value='<VALUE>'}
    if (!input.startsWith("SearchPredicate{") || !input.endsWith("}")) {
      throw new IllegalArgumentException("Invalid SearchPredicate format: " + input);
    }

    // Remove prefix and suffix
    String content = input.substring("SearchPredicate{".length(), input.length() - 1);

    // Extract type
    int typeStart = content.indexOf("type=") + 5;
    int typeEnd = content.indexOf(',', typeStart);
    String type = content.substring(typeStart, typeEnd).trim();

    // Extract field
    int fieldStart = content.indexOf("field='") + 7;
    int fieldEnd = content.indexOf("'", fieldStart);
    String field = content.substring(fieldStart, fieldEnd);

    // Extract value
    int valueStart = content.indexOf("value='") + 7;
    int valueEnd = content.indexOf("'", valueStart);
    String value = content.substring(valueStart, valueEnd);

    // Extract maxDistance
    int maxDistanceStart = content.indexOf("maxDistance=") + 12;
    int maxDistanceEnd = content.indexOf('}', maxDistanceStart);
    double maxDistance = Double.parseDouble(content.substring(maxDistanceStart, maxDistanceEnd));

    return new SearchPredicate(type, field, value, maxDistance);
  }

  @Override
  public String toString() {
    return "SearchPredicate{" +
        "type=" + type +
        ", field='" + field + '\'' +
        ", value='" + value + '\'' +
        ", maxDistance=" + maxDistance +
        '}';
  }

  private boolean evaluateLevenshtein(String searchValue, String fieldValue) {
    LevenshteinDistance distanceCalculator = new LevenshteinDistance();
    if (searchValue == null || fieldValue == null) {
      return false;
    }

    String field = fieldValue.toLowerCase();
    String search = searchValue.toLowerCase();

    int searchLen = search.length();
    int fieldLen = field.length();

    // If search value is longer than field value, just do a direct comparison
    if (searchLen > fieldLen) {
      return distanceCalculator.apply(field, search) <= maxDistance;
    }

    // Search for the best match within the field value using sliding window
    for (int i = 0; i <= fieldLen - searchLen; i++) {
      String sub = field.substring(i, i + searchLen);
      int distance = distanceCalculator.apply(sub, search);

      if (distance <= maxDistance) {
        return true;
      }
    }

    return false;
  }

}
