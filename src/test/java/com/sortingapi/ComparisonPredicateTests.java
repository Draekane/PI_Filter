package com.sortingapi;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.sortingapi.api.Filter;
import com.sortingapi.models.ComparisonPredicate;

import static org.junit.jupiter.api.Assertions.*;

public class ComparisonPredicateTests {

  /**
   * Tests the ComparisonPredicate with various input values. For all the tests
   * that can easily be run on multiple users, this was used. Some later tests
   * needed a little more specificity and would return true for some and false for
   * others, so we grouped like tests and tried to keep the code down to a minimum
   * to make it more readable.
   */
  @ParameterizedTest
  @CsvSource({
      "present, firstName, null, true",
      "present, lastName, null, true",
      "present, role, null, true",
      "present, age, null, true",
      "present, email, null, true",
      "present, nonExistentKey, null, false",
      "matches_regex, email, '.*@ghosted\\.com', true",
      "matches_regex, email, '.*@example\\.com', false",
      "matches_regex, firstName, 'Jimbob_\\d+', true",
      "matches_regex, firstName, '.*Smith', false",
      "matches_regex, age, '\\d+', true",
      "matches_regex, age, '[a-zA-Z]+', false"
  })
  @DisplayName("Test Filter with Comparison Predicates")
  public void testFilterWithComparisonPredicates(String type, String field, String value, boolean expected) {
    // Create a filter with a ComparisonPredicate
    Filter filter = new Filter(new ComparisonPredicate(type, field, value));

    // Create test users
    ArrayList<Map<String, String>> users = testingUtils.createTestUsers(5);

    // Test that all users match the filter (since the predicate always returns
    // false)
    for (Map<String, String> user : users) {
      assertEquals(expected, filter.matches(user),
          "Expected user to " + (expected ? "" : "not ") + "match the filter: " + type + " " + field + " "
              + value);
    }
  }

  /**
   * Tests the ComparisonPredicate with a single user context. Because some of the
   * more specific tests wouldn't pass or fail properly with a large data set
   * (they would work, just our default test data would case some to pass, others
   * to fail), so we grouped ones that were easier to validate with specific data
   * into this test.
   */
  @ParameterizedTest
  @CsvSource({
      "equals, firstName, Jimbob, true",
      "equals, lastName, Litcherholt, true",
      "equals, role, Admin, true",
      "equals, age, 30, true",
      "equals, email, jimbob@ghosted.com, true",
      "equals, nonExistentKey, null, false",
      "less_than, age, 35, true",
      "less_than, age, 25, false",
      "greater_than, age, 25, true",
      "greater_than, age, 35, false"
  })
  @DisplayName("Test ComparisonPredicate with single user context")
  public void testFilterWithComparisonPredicatesSingleUser(String type, String field, String value,
      boolean expected) {
    // Create a filter with a ComparisonPredicate
    Filter filter = new Filter(new ComparisonPredicate(type, field, value));

    Map<String, String> user = testingUtils.createTestUser("Jimbob", "Litcherholt", "Admin", "30",
        "jimbob@ghosted.com");
    assertEquals(expected, filter.matches(user),
        "Expected user to " + (expected ? "" : "not ") + "match the filter: " + type + " " + field + " "
            + value);
  }

}