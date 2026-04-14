package com.filterapi;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.filterapi.api.Filter;
import com.filterapi.models.BooleanPredicate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the FilterAPI class.
 */
public class BooleanPredicateTests {

  /**
   * Tests the BooleanPredicate with various input values. Using ParameterizedTest
   * as all the tests have the same setup, just different outcomes, so rather than
   * writing the same code 10 times, tada!
   */
  @ParameterizedTest
  @CsvSource({
      "true, true",
      "True, true",
      "TRUE, true",
      "false, false",
      "False, false",
      "FALSE, false",
      "T, false",
      "'', false",
      "'null', false",
      "'imaLittleTeapot', false"
  })
  public void testFilterWithBooleanParameterized(String input, boolean expected) {
    // Create a filter with a BooleanPredicate that always returns true
    Filter filter = new Filter(new BooleanPredicate(input));

    System.out.println(filter.toString());

    // Create test users
    ArrayList<Map<String, String>> users = testingUtils.createTestUsers(5);

    // Test that all users match the filter
    for (Map<String, String> user : users) {
      if (expected) {
        assertTrue(filter.matches(user), "Expected user to match the filter");
      } else {
        assertFalse(filter.matches(user), "Expected user to not match the filter");
      }
    }
  }

  @ParameterizedTest
  @CsvSource({
      "true, true",
      "True, true",
      "TRUE, true",
      "false, false",
      "False, false",
      "FALSE, false",
      "T, false",
      "'', false",
      "'null', false",
      "'imaLittleTeapot', false"
  })
  @DisplayName("Test BooleanPredictate parseFromString function")
  public void testBooleanPredicateParseFromString(String input, boolean expected) {
    Filter filter = new Filter(new BooleanPredicate(input));

    String filterString = filter.toString();

    System.out.println("Original Filter String:\n" + filterString);

    Filter parsedFilter = Filter.parseFromString(filterString);

    // Create test users
    ArrayList<Map<String, String>> users = testingUtils.createTestUsers(5);

    // Test that all users match the filter
    for (Map<String, String> user : users) {
      if (expected) {
        assertTrue(parsedFilter.matches(user), "Expected user to match the filter");
      } else {
        assertFalse(parsedFilter.matches(user), "Expected user to not match the filter");
      }
    }

    assertEquals(filterString, parsedFilter.toString(),
        "Expected the original filter string and the parsed filter string to be the same");
  }
}
