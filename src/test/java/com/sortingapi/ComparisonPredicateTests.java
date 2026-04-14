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