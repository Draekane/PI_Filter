package com.filterapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.filterapi.api.Filter;
import com.filterapi.models.SearchPredicate;

public class SearchPredicateTests {
  @ParameterizedTest
  @CsvSource({
      "firstName, Jedediah, true",
      "firstName, Ferdinand, false",
      "lastName, Smyth, true",
      "lastName, Hampton, false",
      "email, @ghosted.com, true",
      "email, @example.com, false"
  })
  @DisplayName("Test Filter with Search Predicates - V1")
  public void testFilterWithSearchPredicates(String field, String value, boolean expected) {
    // Create a filter with a SearchPredicate
    Filter filter = new Filter(new SearchPredicate("levenshtein", field, value, 2.0));

    // Create test users
    Map<String, String> user = testingUtils.createTestUser("Jedadiah", "Smith", "admin", "30", "jedadiah@ghosted.com");

    assertEquals(expected, filter.matches(user),
        "Expected user to " + (expected ? "" : "not ") + "match the filter: " + field + " " + value);
    // }
  }
}
