package com.sortingapi;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.sortingapi.api.Filter;
import com.sortingapi.models.BooleanPredicate;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the SortingAPI class.
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
}
